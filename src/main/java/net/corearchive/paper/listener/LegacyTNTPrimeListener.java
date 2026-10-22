package net.corearchive.paper.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;

import net.corearchive.config.Config;
import net.corearchive.consumer.Queue;
import net.corearchive.listener.block.TNTPrimeUtil;

public final class LegacyTNTPrimeListener extends Queue implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    protected void onTNTPrime(TNTPrimeEvent event) {
        Block block = event.getBlock();
        boolean explosions = Config.getConfig(block.getWorld()).EXPLOSIONS;
        boolean alreadyLogged = event.getReason() == TNTPrimeEvent.PrimeReason.EXPLOSION;
        String user = TNTPrimeUtil.getUser(event.getPrimerEntity());
        if (event.isCancelled() || block.getType() != Material.TNT || !explosions || alreadyLogged) {
            return;
        }
        else if (event.getReason() == TNTPrimeEvent.PrimeReason.FIRE) {
            user = TNTPrimeUtil.getFireUser(block, user);
        }

        Queue.queueBlockBreak(user, block.getState(), Material.TNT, block.getBlockData().getAsString(), 0);
    }

}
