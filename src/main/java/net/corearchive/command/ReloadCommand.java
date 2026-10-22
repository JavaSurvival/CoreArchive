package net.corearchive.command;

import java.util.concurrent.CancellationException;

import org.bukkit.command.CommandSender;

import net.corearchive.config.ConfigHandler;
import net.corearchive.consumer.Consumer;
import net.corearchive.database.Database;
import net.corearchive.database.DuckDBRecovery;
import net.corearchive.language.Phrase;
import net.corearchive.thread.NetworkHandler;
import net.corearchive.utility.Chat;
import net.corearchive.utility.Color;
import net.corearchive.utility.EntitySpawnTracking;
import net.corearchive.utility.ErrorReporter;
import net.corearchive.utility.LookupThrottle;

public class ReloadCommand {

    private static final long CONNECTION_DRAIN_TIMEOUT_MILLIS = 60_000L;

    protected static void runCommand(final CommandSender player, boolean permission, String[] args) {
        if (permission) {
            if (ConfigHandler.converterRunning) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.UPGRADE_IN_PROGRESS));
                return;
            }
            if (ConfigHandler.purgeRunning) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.PURGE_IN_PROGRESS));
                return;
            }
            if (!LookupThrottle.tryAcquire(player.getName(), 100)) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.DATABASE_BUSY));
                return;
            }

            class BasicThread implements Runnable {
                @Override
                public void run() {
                    try {
                        performReload(player);
                    }
                    catch (Exception e) {
                        ErrorReporter.report(e);
                    }
                    finally {
                        LookupThrottle.release(player.getName());
                    }
                }
            }
            try {
                Runnable runnable = new BasicThread();
                Thread thread = new Thread(runnable);
                thread.start();
            }
            catch (RuntimeException | Error e) {
                LookupThrottle.release(player.getName());
                throw e;
            }
        }
        else {
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.NO_PERMISSION));
        }
    }

    static synchronized boolean performReload(CommandSender player) throws InterruptedException {
        final boolean resumePersistenceAfterCancellation = !Consumer.isDatabaseReloadPaused();
        boolean resumePersistence = resumePersistenceAfterCancellation;
        Consumer.OperationStartResult startResult = Consumer.beginDatabaseReload();
        if (startResult != Consumer.OperationStartResult.STARTED) {
            Phrase phrase = startResult == Consumer.OperationStartResult.PURGE_RUNNING ? Phrase.PURGE_IN_PROGRESS
                    : startResult == Consumer.OperationStartResult.ROLLBACK_RUNNING ? Phrase.ROLLBACK_IN_PROGRESS
                            : startResult == Consumer.OperationStartResult.PERSISTENCE_HALTED ? Phrase.DATABASE_PERSISTENCE_HALTED : Phrase.DATABASE_BUSY;
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(phrase));
            return false;
        }
        long deadline = System.nanoTime() + CONNECTION_DRAIN_TIMEOUT_MILLIS * 1_000_000L;
        try {
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_STARTED));
            if (!Consumer.lockDatabaseReload(CONNECTION_DRAIN_TIMEOUT_MILLIS)) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_FAILED));
                return false;
            }
            if (!Database.awaitConnectionDrain(remainingMillis(deadline))) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_FAILED));
                return false;
            }
            resumePersistence = false;
            boolean initialized;
            try {
                initialized = ConfigHandler.performInitialization(false);
            }
            catch (CancellationException e) {
                resumePersistence = resumePersistenceAfterCancellation;
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_FAILED));
                return false;
            }
            if (!initialized) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_FAILED));
                return false;
            }
            EntitySpawnTracking.invalidateDatabaseVerification();
            DuckDBRecovery.reset();
            resumePersistence = true;
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.RELOAD_SUCCESS));

            Thread networkHandler = new Thread(new NetworkHandler(false, false));
            networkHandler.start();
            return true;
        }
        finally {
            Consumer.endDatabaseReload(resumePersistence);
        }
    }

    private static long remainingMillis(long deadline) {
        return Math.max(0L, (deadline - System.nanoTime()) / 1_000_000L);
    }
}
