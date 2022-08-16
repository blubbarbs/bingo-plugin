package com.gmail.blubberalls.custom_events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;
import com.google.common.collect.HashMultimap;

import net.minecraft.world.inventory.ICrafting;

public class CustomInventoryListener implements Listener {
    private static int inventoryChangeSchedulerID = -1;
    private static HashMap<UUID, ItemStack[]> previousInventories = new HashMap<UUID, ItemStack[]>();
    private static HashMultimap<UUID, Integer> changedSlots = HashMultimap.create();
    private static ICrafting nmsSlotListener = new ICrafting() {
        // On slot change
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {            
            Player inventoryHolder = (Player) nmsContainer.getBukkitView().getPlayer();
            int slot = nmsContainer.getBukkitView().convertSlot(rawSlot);
            ItemStack previousStack = previousInventories.get(inventoryHolder.getUniqueId())[slot];
            ItemStack newStack = inventoryHolder.getInventory().getItem(slot);

            if (areStacksEqual(previousStack, newStack)) return;

            changedSlots.put(inventoryHolder.getUniqueId(), nmsContainer.getBukkitView().convertSlot(rawSlot));

            if (inventoryChangeSchedulerID == -1) {
                inventoryChangeSchedulerID = Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(), CustomInventoryListener::callInventoryChangeEvents);
            }
        }

        // On data change (unused)
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, int data) {}
    };


    private static boolean areStacksEqual(ItemStack a, ItemStack b) {
        if (a == b) return true;
        else if (a == null && b != null) return false;
        else if (a != null && b == null) return false;
        else return a.equals(b);
    }

    private static void callInventoryChangeEvents() {
        for (UUID id : changedSlots.keySet()) {
            Player p = Bukkit.getPlayer(id);
            ItemStack[] previousInventory = previousInventories.get(id);
            HashMap<Integer, ItemStack> previous = new HashMap<Integer, ItemStack>();

            for (int i : changedSlots.get(id)) {
                ItemStack previousStack = previousInventory[i];
                ItemStack currentStack = p.getInventory().getItem(i);

                previous.put(i, previousStack);
                previousInventory[i] = currentStack != null ? currentStack.clone() : null;
            }

            PlayerInventoryChangedEvent updateEvent = new PlayerInventoryChangedEvent(p, previous);

            Bukkit.getPluginManager().callEvent(updateEvent);
        }

        changedSlots.clear();
        inventoryChangeSchedulerID = -1;
    }

    public static int getInventoryChangeSchedulerID() {
        return inventoryChangeSchedulerID;
    }

    public static void registerNMSSlotListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener (if there)
        craftView.getHandle().b(nmsSlotListener);
        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }

    public static void registerPlayer(Player p) {
        ItemStack[] inventory = new ItemStack[InventoryType.PLAYER.getDefaultSize()];

        for (int i = 0; i < inventory.length; i++) {            
            inventory[i] = p.getInventory().getItem(i) != null ? p.getInventory().getItem(i).clone() : null;
        }

        previousInventories.put(p.getUniqueId(), inventory);
        registerNMSSlotListener(p.getOpenInventory());
    }

    public static void deregisterPlayer(Player p) {
        previousInventories.remove(p.getUniqueId());
        changedSlots.removeAll(p.getUniqueId());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        deregisterPlayer(event.getPlayer());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        registerNMSSlotListener(event.getView());
    }
}
