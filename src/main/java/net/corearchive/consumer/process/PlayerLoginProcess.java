package net.corearchive.consumer.process;

import java.util.Map;

import org.bukkit.Location;

import net.corearchive.consumer.Consumer;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.logger.PlayerSessionLogger;
import net.corearchive.database.logger.UsernameLogger;
import net.corearchive.model.action.SessionActions;

class PlayerLoginProcess {

    static void process(ConsumerWriteBatch batch, int batchCount, int processId, int id, Object object, int configSessions, int configUsernames, int time, String user) {
        if (object instanceof Location) {
            Map<Integer, String> strings = Consumer.consumerStrings.get(processId);
            if (strings.get(id) != null) {
                String uuid = strings.get(id);
                Location location = (Location) object;
                UsernameLogger.log(batch, user, uuid, configUsernames, time);
                if (configSessions == 1) {
                    PlayerSessionLogger.log(batch, batchCount, user, location, time, SessionActions.LOGIN);
                }
            }
        }
    }
}
