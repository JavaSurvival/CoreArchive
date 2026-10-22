package net.corearchive.consumer.process;

import net.corearchive.database.ConsumerWriteBatch;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.corearchive.consumer.Consumer;
import net.corearchive.database.logger.ContainerBreakLogger;
import net.corearchive.utility.HopperTransactionUtils;

class ContainerBreakProcess {

    static void process(ConsumerWriteBatch preparedStmt, int batchCount, int processId, int id, Material type, String user, Object object) {
        if (object instanceof Location) {
            Location location = (Location) object;
            Map<Integer, ItemStack[]> containers = Consumer.consumerContainers.get(processId);
            if (containers.get(id) != null) {
                ItemStack[] container = containers.get(id);
                String transactionId = HopperTransactionUtils.getTransactionId(location);
                HopperTransactionUtils.synchronizeTransaction(transactionId, () -> ContainerBreakLogger.log(preparedStmt, batchCount, user, location, type, container));
            }
        }
    }
}
