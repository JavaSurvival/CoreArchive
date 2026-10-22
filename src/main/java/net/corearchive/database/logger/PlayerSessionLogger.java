package net.corearchive.database.logger;

import java.util.Locale;

import org.bukkit.Location;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.Database;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.SessionStatement;
import net.corearchive.utility.WorldUtils;

public class PlayerSessionLogger {

    private PlayerSessionLogger() {
        throw new IllegalStateException("Database class");
    }

    public static void log(ConsumerWriteBatch preparedStmt, int batchCount, String user, Location location, int time, int action) {
        try {
            if (ConfigHandler.isBlacklisted(user)) {
                return;
            }
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            int wid = WorldUtils.getWorldId(location.getWorld().getName());
            int userId = ConfigHandler.playerIdCache.get(user.toLowerCase(Locale.ROOT));
            SessionStatement.insert(preparedStmt, batchCount, time, userId, wid, x, y, z, action);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

}
