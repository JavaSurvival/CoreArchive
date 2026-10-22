package net.corearchive.database.logger;

import java.util.Locale;

import org.bukkit.Location;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.Database;
import net.corearchive.database.statement.ChatStatement;
import net.corearchive.utility.WorldUtils;

public class ChatLogger {

    private ChatLogger() {
        throw new IllegalStateException("Database class");
    }

    public static void log(ConsumerWriteBatch preparedStmt, int batchCount, long time, Location location, String user, String message) {
        try {
            if (ConfigHandler.isBlacklisted(user)) {
                return;
            }
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            int wid = WorldUtils.getWorldId(location.getWorld().getName());
            int userId = ConfigHandler.playerIdCache.get(user.toLowerCase(Locale.ROOT));
            ChatStatement.insert(preparedStmt, batchCount, time, userId, wid, x, y, z, message);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

}
