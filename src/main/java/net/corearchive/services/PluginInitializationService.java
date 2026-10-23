package net.corearchive.services;

import java.io.File;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.corearchive.CoreArchive;
import net.corearchive.command.CommandHandler;
import net.corearchive.command.PurgeCommand;
import net.corearchive.command.TabHandler;
import net.corearchive.config.ConfigHandler;
import net.corearchive.consumer.Consumer;
import net.corearchive.language.Language;
import net.corearchive.language.Phrase;
import net.corearchive.listener.ListenerHandler;
import net.corearchive.listener.player.inspector.BaseInspector;
import net.corearchive.thread.CacheHandler;
import net.corearchive.thread.NetworkHandler;
import net.corearchive.thread.Scheduler;
import net.corearchive.thread.TickTimeMonitor;
import net.corearchive.utility.Chat;
import net.corearchive.utility.ChatUtils;
import net.corearchive.utility.Extensions;
import net.corearchive.utility.EntitySpawnTracking;
import net.corearchive.utility.ErrorReporter;

/**
 * Service responsible for plugin initialization tasks
 */
public class PluginInitializationService {

    private static volatile Metrics metrics;

    private PluginInitializationService() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes plugin components and configurations
     *
     * @param plugin
     *            The CoreArchive plugin instance
     * @return true if initialization was successful, false otherwise
     */
    public static boolean initializePlugin(CoreArchive plugin) {
        // Load language phrases
        Language.loadPhrases();

        // Perform version checks
        boolean start = VersionCheckService.performVersionChecks();
        if (!start) {
            return false;
        }

        try {
            // Initialize core components
            PurgeCommand.resetShutdownCancellation();
            Consumer.initialize();

            // Register commands
            registerCommands(plugin);

            // Ensure data directory exists
            createDataDirectory();

            // Initialize configuration
            start = ConfigHandler.performInitialization(true);
            if (start) {
                new ListenerHandler(plugin);
            }
        }
        catch (Exception e) {
            ErrorReporter.report(e);
            return false;
        }

        if (start) {
            // Display startup messages
            displayStartupMessages(plugin);

            // Start background services
            startBackgroundServices(plugin);

            // Start metrics
            enableMetrics(plugin);
        }

        return start;
    }

    /**
     * Registers plugin commands
     *
     * @param plugin
     *            The CoreArchive plugin instance
     */
    private static void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("corearchive").setExecutor(CommandHandler.getInstance());
        plugin.getCommand("corearchive").setTabCompleter(new TabHandler());
        plugin.getCommand("carchive").setExecutor(CommandHandler.getInstance());
        plugin.getCommand("carchive").setTabCompleter(new TabHandler());
        plugin.getCommand("ca").setExecutor(CommandHandler.getInstance());
        plugin.getCommand("ca").setTabCompleter(new TabHandler());
    }

    /**
     * Creates the plugin data directory if it doesn't exist
     */
    private static void createDataDirectory() {
        boolean exists = (new File(ConfigHandler.path)).exists();
        if (!exists) {
            new File(ConfigHandler.path).mkdir();
        }
    }

    /**
     * Displays startup messages in the console
     *
     * @param plugin
     *            The CoreArchive plugin instance
     */
    private static void displayStartupMessages(JavaPlugin plugin) {
        PluginDescriptionFile pluginDescription = plugin.getDescription();
        ChatUtils.sendConsoleComponentStartup(Bukkit.getServer().getConsoleSender(), Phrase.build(Phrase.ENABLE_SUCCESS, ConfigHandler.EDITION_NAME));

        if (ConfigHandler.databaseType.isMySQL()) {
            Chat.console(Phrase.build(Phrase.USING_MYSQL));
        }
        else if (ConfigHandler.databaseType.isSQLite()) {
            Chat.console(Phrase.build(Phrase.USING_SQLITE));
        }
        else {
            Chat.console(Phrase.build(Phrase.USING_DATABASE, ConfigHandler.databaseType.getDisplayName()));
        }

        Chat.console("--------------------");
        Chat.console(Phrase.build(Phrase.ENJOY_COREPROTECT, pluginDescription.getName()));
        Chat.console(Phrase.build(Phrase.LINK_DISCORD, "www.coreprotect.net/discord/"));
        Chat.console("--------------------");
    }

    /**
     * Starts background services
     *
     * @param plugin
     *            The CoreArchive plugin instance
     */
    private static void startBackgroundServices(CoreArchive plugin) {
        // Start network handler
        Scheduler.scheduleSyncDelayedTask(plugin, () -> {
            try {
                Thread networkHandler = new Thread(new NetworkHandler(true, true));
                networkHandler.start();
            }
            catch (Exception e) {
                ErrorReporter.report(e);
            }
        }, 0);

        // Start tick time monitor (only used where native tick timings are unavailable)
        TickTimeMonitor.initialize(plugin);

        // Start cache cleanup thread
        CacheHandler.startThread();

        // Start inspector lookup threads
        BaseInspector.startLookups();

        Consumer.startConsumer();
        EntitySpawnTracking.initializeLoadedEntities();
        Extensions.startBackgroundService();
    }

    /**
     * Enables metrics reporting
     *
     * @param plugin
     *            The CoreArchive plugin instance
     */
    private static void enableMetrics(JavaPlugin plugin) {
        try {
            metrics = new Metrics(plugin, 2876);
        }
        catch (Exception e) {
            // Failed to connect to bStats server or something else went wrong
        }
    }

    /**
     * Stops the bStats scheduler so its thread does not outlive a disable
     */
    public static void disableMetrics() {
        Metrics current = metrics;
        metrics = null;
        if (current == null) {
            return;
        }

        try {
            current.shutdown();
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }
    }
}
