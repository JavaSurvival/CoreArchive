package net.corearchive.consumer.process;

import java.sql.Statement;

import org.bukkit.block.BlockState;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.statement.SkullStatement;
import net.corearchive.utility.BlockUtils;

class SkullUpdateProcess {

    static void process(Statement statement, Object object, int rowId) {
        /*
         * We're switching blocks around quickly.
         * This block could already be removed again by the time the server tries to modify it.
         * Ignore any errors.
         */
        if (object instanceof BlockState) {
            BlockState block = (BlockState) object;
            String query = "SELECT owner, skin FROM " + ConfigHandler.prefix + "skull WHERE rowid=" + rowId + " LIMIT 1 OFFSET 0";
            SkullStatement.getData(statement, block, query);
            BlockUtils.updateBlock(block);
        }
    }
}
