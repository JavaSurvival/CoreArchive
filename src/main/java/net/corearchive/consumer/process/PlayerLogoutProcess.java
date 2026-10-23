package net.corearchive.consumer.process;

import net.corearchive.database.ConsumerWriteBatch;

import org.bukkit.Location;

import net.corearchive.database.logger.PlayerSessionLogger;
import net.corearchive.model.action.SessionActions;

class PlayerLogoutProcess {

    static void process(ConsumerWriteBatch preparedStmt, int batchCount, Object object, int time, String user) {
        if (object instanceof Location) {
            Location location = (Location) object;
            PlayerSessionLogger.log(preparedStmt, batchCount, user, location, time, SessionActions.LOGOUT);
        }
    }
}
