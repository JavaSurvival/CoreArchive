package net.corearchive.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.corearchive.config.Config;
import net.corearchive.consumer.Queue;

public final class PlayerQuitListener extends Queue implements Listener {

    public static void queuePlayerQuit(Player player) {
        if (Config.getConfig(player.getWorld()).PLAYER_SESSIONS) {
            int time = (int) (System.currentTimeMillis() / 1000L);
            Queue.queuePlayerQuit(player, time);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        queuePlayerQuit(event.getPlayer());
    }

}
