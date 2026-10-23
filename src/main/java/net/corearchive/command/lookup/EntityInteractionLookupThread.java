package net.corearchive.command.lookup;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.Database;
import net.corearchive.database.lookup.EntityInteractionLookup;
import net.corearchive.language.Phrase;
import net.corearchive.utility.Chat;
import net.corearchive.utility.Color;
import net.corearchive.utility.ErrorReporter;
import net.corearchive.utility.LookupThrottle;

public final class EntityInteractionLookupThread implements Runnable {
    private final CommandSender player;
    private final Command command;
    private final int page;
    private final int limit;

    public EntityInteractionLookupThread(CommandSender player, Command command, int page, int limit) {
        this.player = player;
        this.command = command;
        this.page = page;
        this.limit = limit;
    }

    @Override
    public void run() {
        if (!LookupThrottle.tryAcquire(player.getName(), 50)) {
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.DATABASE_BUSY));
            return;
        }

        try (Connection connection = Database.getConnection(true)) {
            if (connection == null) {
                Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.DATABASE_BUSY));
                return;
            }

            Integer entitySpawnRowId = ConfigHandler.lookupEntityInteraction.get(player.getName());
            try (Statement statement = connection.createStatement()) {
                List<String> results = EntityInteractionLookup.performLookup(command.getName(), statement, player, page, limit, entitySpawnRowId);
                for (String result : results) {
                    Chat.sendComponent(player, result);
                }
            }
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }
        finally {
            LookupThrottle.release(player.getName());
        }
    }
}
