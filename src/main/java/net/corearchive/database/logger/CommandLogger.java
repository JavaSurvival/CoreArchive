package net.corearchive.database.logger;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.corearchive.CoreProtect;
import net.corearchive.config.Config;
import net.corearchive.config.ConfigHandler;
import net.corearchive.database.Database;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.CommandStatement;
import net.corearchive.database.statement.UserStatement;
import net.corearchive.event.CoreProtectPreLogEvent;
import net.corearchive.utility.WorldUtils;

public class CommandLogger {

    private CommandLogger() {
        throw new IllegalStateException("Database class");
    }

    public static void log(ConsumerWriteBatch preparedStmt, int batchCount, long time, Location location, String user, String message) {
        try {
            if (ConfigHandler.isBlacklisted(user)) {
                return;
            }
            if (ConfigHandler.isBlacklisted(((message + " ").split(" "))[0])) {
                return;
            }

            CoreProtectPreLogEvent event = new CoreProtectPreLogEvent(user, location, CoreProtectPreLogEvent.Action.PLAYER_COMMAND, -1, null, null, message);
            if (Config.getGlobal().API_ENABLED && !Bukkit.isPrimaryThread()) {
                CoreProtect.getInstance().getServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
                return;
            }

            int userId = UserStatement.getId(preparedStmt, event.getUser(), true);
            Location eventLocation = event.getLocation();
            int wid = WorldUtils.getWorldId(eventLocation.getWorld().getName());
            int x = eventLocation.getBlockX();
            int y = eventLocation.getBlockY();
            int z = eventLocation.getBlockZ();
            CommandStatement.insert(preparedStmt, batchCount, time, userId, wid, x, y, z, message);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

}
