package net.corearchive.listener.entity;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import net.corearchive.listener.player.InventoryChangeListener;
import net.corearchive.utility.EntitySpawnTracking;

public final class LegacyEntityChunkListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            EntitySpawnTracking.handleLoad(entity);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        for (Entity entity : chunk.getEntities()) {
            InventoryChangeListener.flushEntityContainer(entity);
            EntitySpawnTracking.handleUnload(entity);
        }
    }
}
