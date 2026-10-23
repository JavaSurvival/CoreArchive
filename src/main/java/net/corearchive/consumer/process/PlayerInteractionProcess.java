package net.corearchive.consumer.process;

import net.corearchive.database.ConsumerWriteBatch;

import org.bukkit.Location;
import org.bukkit.Material;

import net.corearchive.database.logger.PlayerInteractLogger;

class PlayerInteractionProcess {

    static void process(ConsumerWriteBatch preparedStmt, int batchCount, String user, Object object, Material type, String blockData) {
        if (object instanceof Location) {
            Location location = (Location) object;
            PlayerInteractLogger.log(preparedStmt, batchCount, user, location, type, blockData);
        }
    }
}
