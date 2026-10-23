package net.corearchive.database;

import java.util.List;
import java.util.UUID;

import net.corearchive.model.entity.EntityContainerRollbackUpdate;
import net.corearchive.model.entity.EntitySpawnData;
import net.corearchive.model.entity.EntitySpawnIdentity;

public interface ConsumerEntitySpawnUpdates extends AutoCloseable {

    default void prefetch(List<EntitySpawnData> updates) throws Exception {
    }

    EntitySpawnIdentity apply(EntitySpawnData data);

    void applyCombined(EntityContainerRollbackUpdate update, Database.SavepointOperation rowUpdate);

    void identityFound(UUID uuid);

    void afterCommit(boolean committed);

    void afterRetain();

    void afterDiscard();

    @Override
    void close() throws Exception;

}
