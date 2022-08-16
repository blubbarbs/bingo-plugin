package com.gmail.blubberalls.custom_events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.Bingo;

import net.minecraft.world.inventory.ICrafting;

public class CustomEvents implements Listener {
    private static int existEventSchedulerID = -1;
    private static int inventoryChangeSchedulerID = -1;
    private static HashMap<UUID, ItemStack[]> dummyInventories = new HashMap<UUID, ItemStack[]>();
    private static HashSet<UUID> updatedInventoryUUIDs = new HashSet<UUID>();
    private static ICrafting nmsSlotListener = new ICrafting() {
        // On slot change
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {            
            Player inventoryHolder = (Player) nmsContainer.getBukkitView().getPlayer();

            Bukkit.getLogger().info("NMS LISTENER");
            updatedInventoryUUIDs.add(inventoryHolder.getUniqueId());

            if (inventoryChangeSchedulerID == -1) {
                inventoryChangeSchedulerID = Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(), CustomEvents::callInventoryChangeEvents);
            }
        }

        // On data change (unused)
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, int data) {}
    };

    static {
        existEventSchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingo.getInstance(), CustomEvents::callExistEvent , 0L, 10L);
    }

    public static int getExistSchedulerID() {
        return existEventSchedulerID;
    }

    public static int getInventoryChangeSchedulerID() {
        return inventoryChangeSchedulerID;
    }

    private static void callExistEvent() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerExistEvent event = new PlayerExistEvent(p);

            Bukkit.getPluginManager().callEvent(event);
        }
    }

    private static void callInventoryChangeEvents() {
        for (UUID id : updatedInventoryUUIDs) {
            Player p = Bukkit.getPlayer(id);
            ItemStack[] newContents = p.getInventory().getContents();
            Bukkit.getLogger().info("LENGTH " + newContents.length);
            ItemStack[] previousContents = dummyInventories.get(id);

            InventoryUpdateEvent updateEvent = new InventoryUpdateEvent(p, previousContents);

            Bukkit.getLogger().info("CALLED EVENT");
            Bukkit.getPluginManager().callEvent(updateEvent);

            for (int i = 0; i < previousContents.length; i++) {
                if (newContents[i] != null) {
                    previousContents[i] = newContents[i].clone();
                }
            }
        }

        inventoryChangeSchedulerID = -1;
    }

    public static void registerInventoryListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener (if there)
        craftView.getHandle().b(nmsSlotListener);
        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }

    public static void registerPlayer(Player p) {
        //CraftInventoryCreator.INSTANCE.createInventory(null, InventoryType.PLAYER);
        ItemStack[] inventory = new ItemStack[InventoryType.PLAYER.getDefaultSize()];

        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            
            inventory[i] = stack != null ? p.getInventory().getItem(i).clone() : null;
        }

        Bukkit.getLogger().info("REGISTERED PLAYER");
        dummyInventories.put(p.getUniqueId(), inventory);
        Bukkit.getLogger().info("" + dummyInventories.keySet().size());
        registerInventoryListener(p.getOpenInventory());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        registerInventoryListener(event.getView());
    }
}
