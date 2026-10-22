package net.corearchive.listener.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;

import net.corearchive.listener.player.InventoryChangeListener;
import net.corearchive.utility.EntitySpawnTracking;

public final class EntityChunkListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            EntitySpawnTracking.handleLoad(entity);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities()) {
            InventoryChangeListener.flushEntityContainer(entity);
            EntitySpawnTracking.handleUnload(entity);
        }
    }
}
