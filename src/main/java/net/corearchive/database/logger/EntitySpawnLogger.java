package net.corearchive.database.logger;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.corearchive.CoreProtect;
import net.corearchive.config.Config;
import net.corearchive.config.ConfigHandler;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.BlockStatement;
import net.corearchive.database.statement.EntitySpawnStatement;
import net.corearchive.database.statement.UserStatement;
import net.corearchive.event.CoreProtectPreLogEvent;
import net.corearchive.model.action.LookupActions;
import net.corearchive.model.entity.EntitySpawnData;
import net.corearchive.model.entity.EntitySpawnIdentity;
import net.corearchive.utility.EntityUtils;
import net.corearchive.utility.WorldUtils;

public final class EntitySpawnLogger {

    private EntitySpawnLogger() {
        throw new IllegalStateException("Database class");
    }

    public static EntitySpawnIdentity logIdentity(ConsumerWriteBatch batch, String user, EntitySpawnData data, EntitySpawnIdentity existingIdentity) throws Exception {
        if (existingIdentity != null && existingIdentity.hasSpawnLog()) {
            return existingIdentity;
        }
        if (ConfigHandler.isBlacklisted(user)) {
            return null;
        }

        EntityType type = data.getEntityType();
        if (type == null || ConfigHandler.isBlacklisted(user, type.getKey().toString())) {
            return null;
        }

        Location initialLocation = data.getLocation();
        CoreProtectPreLogEvent event = new CoreProtectPreLogEvent(user, initialLocation.clone(), CoreProtectPreLogEvent.Action.ENTITY_SPAWN, LookupActions.ENTITY_SPAWN, null, type, null);
        if (Config.getGlobal().API_ENABLED && !Bukkit.isPrimaryThread()) {
            CoreProtect.getInstance().getServer().getPluginManager().callEvent(event);
        }
        if (event.isCancelled()) {
            return null;
        }

        int time = (int) (System.currentTimeMillis() / 1000L);
        int userId = UserStatement.getId(batch, event.getUser(), true);
        Location location = event.getLocation();
        int worldId = WorldUtils.getWorldId(location.getWorld().getName());
        int entityId = EntityUtils.getEntityId(type);

        EntitySpawnIdentity[] identity = { existingIdentity };
        batch.executeAtomically("entity_spawn_log", () -> {
            if (identity[0] == null) {
                int trackingRowId = EntitySpawnStatement.insert(batch, time, data, location);
                identity[0] = new EntitySpawnIdentity(trackingRowId, data.getUuid(), worldId, location.getX(), location.getY(), location.getZ());
            }
            long blockRowId = BlockStatement.insertImmediate(batch, time, userId, worldId, location.getBlockX(), location.getBlockY(), location.getBlockZ(), entityId, identity[0].getRowId(), null, null, LookupActions.ENTITY_SPAWN, 0);
            EntitySpawnStatement.linkBlock(batch, identity[0].getRowId(), blockRowId);
        });
        identity[0].markSpawnLogged();
        return identity[0];
    }
}
