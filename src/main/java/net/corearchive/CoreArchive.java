package net.corearchive;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.corearchive.config.ConfigHandler;
import net.corearchive.language.Phrase;
import net.corearchive.services.PluginInitializationService;
import net.corearchive.services.ShutdownService;
import net.corearchive.utility.Chat;

/**
 * Main class for the CoreArchive plugin
 */
public final class CoreArchive extends JavaPlugin {

    private static CoreArchive instance;
    /**
     * Get the instance of CoreArchive
     *
     * @return This CoreArchive instance
     */
    public static CoreArchive getInstance() {
        return instance;
    }

    private final CoreProtectAPI api = new CoreProtectAPI();

    /**
     * Get the CoreArchive API
     *
     * @return The CoreArchive API
     */
    public CoreProtectAPI getAPI() {
        return api;
    }

    @Override
    public void onEnable() {
        // Set plugin instance and data folder path
        instance = this;
        ConfigHandler.path = this.getDataFolder().getPath() + File.separator;

        // Initialize plugin using the initialization service
        boolean initialized = PluginInitializationService.initializePlugin(this);

        // Disable plugin if initialization failed
        if (!initialized) {
            Chat.console(Phrase.build(Phrase.ENABLE_FAILED, ConfigHandler.EDITION_NAME));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        ShutdownService.safeShutdown(this);
    }

    public boolean isAdvancedChestsEnabled() {
        Plugin advancedChests = getServer().getPluginManager().getPlugin("AdvancedChests");
        return advancedChests != null && advancedChests.isEnabled();
    }
}
