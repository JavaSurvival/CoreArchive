package net.corearchive.consumer.process;

import net.corearchive.database.ConsumerWriteBatch;

import net.corearchive.database.logger.EntitySpawnLogger;
import net.corearchive.model.entity.EntitySpawnData;
import net.corearchive.model.entity.EntitySpawnIdentity;

final class EntitySpawnLogProcess {

    private EntitySpawnLogProcess() {
    }

    static EntitySpawnIdentity process(ConsumerWriteBatch batch, Object object, String user, EntitySpawnIdentity identity) throws Exception {
        if (object instanceof EntitySpawnData) {
            EntitySpawnData data = (EntitySpawnData) object;
            return EntitySpawnLogger.logIdentity(batch, user, data, identity);
        }
        return null;
    }
}
