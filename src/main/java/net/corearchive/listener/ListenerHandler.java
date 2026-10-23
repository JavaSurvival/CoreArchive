package net.corearchive.listener;

import org.bukkit.plugin.PluginManager;

import net.corearchive.CoreProtect;
import net.corearchive.listener.block.BlockBreakListener;
import net.corearchive.listener.block.BlockBurnListener;
import net.corearchive.listener.block.BlockDispenseListener;
import net.corearchive.listener.block.BlockExplodeListener;
import net.corearchive.listener.block.BlockFadeListener;
import net.corearchive.listener.block.BlockFertilizeListener;
import net.corearchive.listener.block.BlockFormListener;
import net.corearchive.listener.block.BlockFromToListener;
import net.corearchive.listener.block.BlockIgniteListener;
import net.corearchive.listener.block.BlockPistonListener;
import net.corearchive.listener.block.BlockPlaceListener;
import net.corearchive.listener.block.BlockSpreadListener;
import net.corearchive.listener.block.CampfireStartListener;
import net.corearchive.listener.block.TNTPrimeListener;
import net.corearchive.listener.block.TNTPrimeUtil;
import net.corearchive.listener.channel.PluginChannelHandshakeListener;
import net.corearchive.listener.channel.PluginChannelListener;
import net.corearchive.listener.entity.CreatureSpawnListener;
import net.corearchive.listener.entity.EntityBlockFormListener;
import net.corearchive.listener.entity.EntityChangeBlockListener;
import net.corearchive.listener.entity.EntityDamageByBlockListener;
import net.corearchive.listener.entity.EntityDamageByEntityListener;
import net.corearchive.listener.entity.EntityDeathListener;
import net.corearchive.listener.entity.EntityExplodeListener;
import net.corearchive.listener.entity.EntityInteractListener;
import net.corearchive.listener.entity.EntityPlaceListener;
import net.corearchive.listener.entity.EntityChunkListener;
import net.corearchive.listener.entity.LegacyEntityChunkListener;
import net.corearchive.listener.entity.EntityPickupItemListener;
import net.corearchive.listener.entity.CushionBreakListener;
import net.corearchive.listener.entity.EntitySpawnListener;
import net.corearchive.listener.entity.EntityTransformListener;
import net.corearchive.listener.entity.HangingBreakByEntityListener;
import net.corearchive.listener.entity.HangingBreakListener;
import net.corearchive.listener.entity.HangingPlaceListener;
import net.corearchive.listener.entity.VehicleDamageListener;
import net.corearchive.listener.entity.VehicleDestroyListener;
import net.corearchive.listener.entity.TrackedEntityRemoveListener;
import net.corearchive.listener.entity.TrackedEntityTeleportListener;
import net.corearchive.listener.player.ArmorStandManipulateListener;
import net.corearchive.listener.player.CraftItemListener;
import net.corearchive.listener.player.EntityInteractionListener;
import net.corearchive.listener.player.FoodLevelChangeListener;
import net.corearchive.listener.player.InventoryChangeListener;
import net.corearchive.listener.player.InventoryClickListener;
import net.corearchive.listener.player.PlayerBucketEmptyListener;
import net.corearchive.listener.player.PlayerBucketFillListener;
import net.corearchive.listener.player.PlayerChatListener;
import net.corearchive.listener.player.PlayerCommandListener;
import net.corearchive.listener.player.PlayerDeathListener;
import net.corearchive.listener.player.PlayerDropItemListener;
import net.corearchive.listener.player.PlayerInteractEntityListener;
import net.corearchive.listener.player.PlayerInteractListener;
import net.corearchive.listener.player.PlayerItemBreakListener;
import net.corearchive.listener.player.PlayerJoinListener;
import net.corearchive.listener.player.PlayerPickupArrowListener;
import net.corearchive.listener.player.PlayerQuitListener;
import net.corearchive.listener.player.PlayerTakeLecternBookListener;
import net.corearchive.listener.player.ProjectileLaunchListener;
import net.corearchive.listener.player.SignChangeListener;
import net.corearchive.listener.player.SpawnEggUseListener;
import net.corearchive.listener.world.ChunkPopulateListener;
import net.corearchive.listener.world.LeavesDecayListener;
import net.corearchive.listener.world.PortalCreateListener;
import net.corearchive.listener.world.StructureGrowListener;
import net.corearchive.paper.listener.BlockPreDispenseListener;
import net.corearchive.paper.listener.CopperGolemChestListener;
import net.corearchive.paper.listener.CushionListener;
import net.corearchive.paper.listener.FlowerPotManipulateListener;
import net.corearchive.paper.listener.LegacyTNTPrimeListener;
import net.corearchive.paper.listener.PaperChatListener;
import net.corearchive.spigot.listener.CushionPlaceListener;

public final class ListenerHandler {

    public ListenerHandler(CoreProtect plugin) {

        PluginManager pluginManager = plugin.getServer().getPluginManager();

        // Paper Listeners / Fallbacks (Block Listeners)
        try {
            Class.forName("io.papermc.paper.event.block.BlockPreDispenseEvent"); // Paper 1.16+
            pluginManager.registerEvents(new BlockPreDispenseListener(), plugin);
        }
        catch (Exception e) {
            BlockPreDispenseListener.useBlockPreDispenseEvent = false;
        }

        try {
            Class.forName("io.papermc.paper.event.entity.ItemTransportingEntityValidateTargetEvent"); // Paper 1.21.10+
            pluginManager.registerEvents(new CopperGolemChestListener(plugin), plugin);
        }
        catch (Exception e) {
            // Ignore registration failures to remain compatible with older servers.
        }

        try {
            Class.forName("io.papermc.paper.event.entity.EntityBreakEvent");
            pluginManager.registerEvents(new CushionListener(), plugin);
        }
        catch (ClassNotFoundException e) {
            try {
                Class.forName("org.bukkit.entity.Cushion");
                pluginManager.registerEvents(new CushionPlaceListener(), plugin);
            }
            catch (ClassNotFoundException ignored) {
            }
        }

        // Block Listeners
        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new BlockBurnListener(), plugin);
        pluginManager.registerEvents(new BlockDispenseListener(), plugin);
        pluginManager.registerEvents(new BlockExplodeListener(), plugin);
        pluginManager.registerEvents(new BlockFadeListener(), plugin);
        pluginManager.registerEvents(new BlockFertilizeListener(), plugin);
        pluginManager.registerEvents(new BlockFormListener(), plugin);
        pluginManager.registerEvents(new BlockFromToListener(), plugin);
        pluginManager.registerEvents(new BlockIgniteListener(), plugin);
        pluginManager.registerEvents(new BlockPistonListener(), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), plugin);
        pluginManager.registerEvents(new BlockSpreadListener(), plugin);
        try {
            Class.forName("org.bukkit.event.block.CampfireStartEvent"); // Bukkit 1.20+
            pluginManager.registerEvents(new CampfireStartListener(), plugin);
        }
        catch (Exception e) {
            CampfireStartListener.useCampfireStartEvent = false;
        }
        try {
            Class.forName("org.bukkit.event.block.TNTPrimeEvent"); // Bukkit 1.20+
            pluginManager.registerEvents(new TNTPrimeListener(), plugin);
            TNTPrimeUtil.useTNTPrimeEvent = true;
        }
        catch (Exception e) {
            try {
                Class.forName("com.destroystokyo.paper.event.block.TNTPrimeEvent"); // Paper 1.16+
                pluginManager.registerEvents(new LegacyTNTPrimeListener(), plugin);
                TNTPrimeUtil.useTNTPrimeEvent = true;
            }
            catch (Exception ignored) {
                // Ignore registration failures to remain compatible with older servers.
            }
        }

        // Entity Listeners
        pluginManager.registerEvents(new CreatureSpawnListener(), plugin);
        pluginManager.registerEvents(new EntityBlockFormListener(), plugin);
        pluginManager.registerEvents(new EntityChangeBlockListener(), plugin);
        pluginManager.registerEvents(new EntityDamageByBlockListener(), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), plugin);
        pluginManager.registerEvents(new EntityDeathListener(), plugin);
        pluginManager.registerEvents(new EntityExplodeListener(), plugin);
        pluginManager.registerEvents(new EntityInteractListener(), plugin);
        pluginManager.registerEvents(new EntityPlaceListener(), plugin);
        pluginManager.registerEvents(new EntityPickupItemListener(), plugin);
        pluginManager.registerEvents(new EntitySpawnListener(), plugin);
        pluginManager.registerEvents(new CushionBreakListener(), plugin);
        pluginManager.registerEvents(new EntityTransformListener(), plugin);
        pluginManager.registerEvents(new HangingPlaceListener(), plugin);
        pluginManager.registerEvents(new HangingBreakListener(), plugin);
        pluginManager.registerEvents(new HangingBreakByEntityListener(), plugin);
        pluginManager.registerEvents(new VehicleDamageListener(), plugin);
        pluginManager.registerEvents(new VehicleDestroyListener(), plugin);
        pluginManager.registerEvents(new TrackedEntityTeleportListener(), plugin);
        try {
            Class.forName("org.bukkit.event.world.EntitiesUnloadEvent");
            pluginManager.registerEvents(new EntityChunkListener(), plugin);
        }
        catch (Exception e) {
            pluginManager.registerEvents(new LegacyEntityChunkListener(), plugin);
        }
        try {
            Class.forName("org.bukkit.event.entity.EntityRemoveEvent");
            pluginManager.registerEvents(new TrackedEntityRemoveListener(), plugin);
        }
        catch (Exception e) {
            // Entity removal causes are unavailable on older Bukkit versions.
        }
        // Paper Listeners / Fallbacks (Player Listeners)
        try {
            Class.forName("net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer"); // Paper 1.16+
            pluginManager.registerEvents(new PaperChatListener(), plugin);
        }
        catch (Exception e) {
            pluginManager.registerEvents(new PlayerChatListener(), plugin);
        }
        try {
            Class.forName("io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent");
            pluginManager.registerEvents(new FlowerPotManipulateListener(), plugin);
        }
        catch (Exception e) {
            // Ignore registration failures to remain compatible with older servers.
        }

        // Player Listeners
        pluginManager.registerEvents(new ArmorStandManipulateListener(), plugin);
        pluginManager.registerEvents(new CraftItemListener(), plugin);
        pluginManager.registerEvents(new EntityInteractionListener(), plugin);
        pluginManager.registerEvents(new FoodLevelChangeListener(), plugin);
        pluginManager.registerEvents(new InventoryChangeListener(), plugin);
        pluginManager.registerEvents(new InventoryClickListener(), plugin);
        pluginManager.registerEvents(new PlayerBucketEmptyListener(), plugin);
        pluginManager.registerEvents(new PlayerBucketFillListener(), plugin);
        pluginManager.registerEvents(new PlayerCommandListener(), plugin);
        pluginManager.registerEvents(new PlayerDeathListener(), plugin);
        pluginManager.registerEvents(new PlayerDropItemListener(), plugin);
        pluginManager.registerEvents(new PlayerPickupArrowListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractEntityListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlayerItemBreakListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
        pluginManager.registerEvents(new SignChangeListener(), plugin);
        pluginManager.registerEvents(new SpawnEggUseListener(), plugin);
        pluginManager.registerEvents(new PlayerTakeLecternBookListener(), plugin);
        pluginManager.registerEvents(new ProjectileLaunchListener(), plugin);

        // World Listeners
        pluginManager.registerEvents(new ChunkPopulateListener(), plugin);
        pluginManager.registerEvents(new LeavesDecayListener(), plugin);
        pluginManager.registerEvents(new PortalCreateListener(), plugin);
        pluginManager.registerEvents(new StructureGrowListener(), plugin);

        // Plugin channel events
        pluginManager.registerEvents(new PluginChannelListener(), plugin);
    }

    public static void registerNetworking() {
        PluginChannelHandshakeListener handshakeListener = new PluginChannelHandshakeListener();
        CoreProtect.getInstance().getServer().getMessenger().registerIncomingPluginChannel(CoreProtect.getInstance(), PluginChannelHandshakeListener.pluginChannel, handshakeListener);
        CoreProtect.getInstance().getServer().getPluginManager().registerEvents(handshakeListener, CoreProtect.getInstance());
        CoreProtect.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(CoreProtect.getInstance(), PluginChannelHandshakeListener.pluginChannel);
        CoreProtect.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(CoreProtect.getInstance(), PluginChannelListener.pluginChannel);
    }

    public static void unregisterNetworking() {
        CoreProtect.getInstance().getServer().getMessenger().unregisterIncomingPluginChannel(CoreProtect.getInstance(), PluginChannelHandshakeListener.pluginChannel);
        CoreProtect.getInstance().getServer().getMessenger().unregisterOutgoingPluginChannel(CoreProtect.getInstance(), PluginChannelHandshakeListener.pluginChannel);
        CoreProtect.getInstance().getServer().getMessenger().unregisterOutgoingPluginChannel(CoreProtect.getInstance(), PluginChannelListener.pluginChannel);
    }

}
