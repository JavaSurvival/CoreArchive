package net.corearchive.listener.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;

import net.corearchive.consumer.Queue;
import net.corearchive.listener.player.EntityInteractionListener;
import net.corearchive.listener.player.InventoryChangeListener;
import net.corearchive.utility.EntitySpawnTracking;

public final class TrackedEntityRemoveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemove(EntityRemoveEvent event) {
        Entity entity = event.getEntity();
        switch (event.getCause()) {
            case UNLOAD:
                return;
            case DEATH:
            case HIT:
                if (!EntitySpawnTracking.isCushion(entity)) {
                    return;
                }
                break;
            case PLUGIN:
                if (EntitySpawnTracking.isCoreProtectRemoval(entity.getUniqueId())) {
                    return;
                }
                break;
            default:
                break;
        }

        if (EntitySpawnTracking.isTrackedOrPendingIdentity(entity)) {
            InventoryChangeListener.flushEntityContainer(entity);
            EntityInteractionListener.flushPendingInteractions(entity);
            Queue.queueEntitySpawnRemoved(entity);
            EntitySpawnTracking.clearTracking(entity.getUniqueId());
        }
    }
}
