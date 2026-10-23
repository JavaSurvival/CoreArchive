package net.corearchive.database.logger;


import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.Database;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.SkullStatement;
import net.corearchive.paper.PaperAdapter;

public class SkullPlaceLogger {

    private SkullPlaceLogger() {
        throw new IllegalStateException("Database class");
    }

    public static void log(ConsumerWriteBatch preparedStmt, ConsumerWriteBatch preparedStmt2, int batchCount, String user, BlockState block, int replaceType, int replaceData) {
        try {
            if (ConfigHandler.isBlacklisted(user) || block == null) {
                return;
            }
            int time = (int) (System.currentTimeMillis() / 1000L);
            Material type = block.getType();
            int skullKey = 0;

            if (block instanceof Skull) {
                Skull skull = (Skull) block;
                String skullOwner = "";
                String skullSkin = null;
                skullOwner = PaperAdapter.ADAPTER.getSkullOwner(skull);
                skullSkin = PaperAdapter.ADAPTER.getSkullSkin(skull);
                if ((skullOwner != null && skullOwner.length() > 0) || (skullSkin != null && skullSkin.length() > 0)) {
                    skullKey = SkullStatement.insert(preparedStmt2, time, skullOwner, skullSkin);
                }
            }

            BlockPlaceLogger.log(preparedStmt, batchCount, user, block, replaceType, replaceData, type, skullKey, true, null, null, null);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

}
