package net.corearchive.paper.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.corearchive.config.Config;
import net.corearchive.consumer.Queue;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public final class PaperChatListener extends Queue implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (message.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();
        if (event.isCancelled() && !Config.getConfig(player.getWorld()).LOG_CANCELLED_CHAT) {
            return;
        }

        if (!message.startsWith("/") && Config.getConfig(player.getWorld()).PLAYER_MESSAGES) {
            long timestamp = System.currentTimeMillis() / 1000L;
            Queue.queuePlayerChat(player, message, timestamp);
        }
    }
}
