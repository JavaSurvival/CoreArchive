package net.corearchive.listener.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

import net.corearchive.config.Config;
import net.corearchive.consumer.Queue;

public final class LeavesDecayListener extends Queue implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    protected void onLeavesDecay(LeavesDecayEvent event) {
        World world = event.getBlock().getWorld();
        if (!event.isCancelled() && Config.getConfig(world).LEAF_DECAY) {
            String player = "#decay";
            Block block = event.getBlock();
            Material type = event.getBlock().getType();

            Queue.queueBlockBreak(player, block.getState(), type, event.getBlock().getBlockData().getAsString(), 0);
        }
    }
}
