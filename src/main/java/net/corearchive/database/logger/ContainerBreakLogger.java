package net.corearchive.database.logger;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.corearchive.consumer.Queue;
import net.corearchive.database.Database;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.model.item.ItemTransactionActions;
import net.corearchive.utility.HopperTransactionUtils;
import net.corearchive.utility.ItemUtils;

public class ContainerBreakLogger {

    private ContainerBreakLogger() {
        throw new IllegalStateException("Database class");
    }

    public static void log(ConsumerWriteBatch preparedStmt, int batchCount, String player, Location l, Material type, ItemStack[] oldInventory) {
        try {
            ItemUtils.mergeItems(type, oldInventory);
            ContainerLogger.logTransaction(preparedStmt, batchCount, player, type, null, oldInventory, ItemTransactionActions.REMOVE, l);
            String loggingContainerId = HopperTransactionUtils.getLoggingId(player, l);

            // If there was a pending chest transaction, it would have already been processed.
            Queue.removeForceContainer(loggingContainerId);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

}
