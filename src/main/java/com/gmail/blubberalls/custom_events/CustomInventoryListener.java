package com.gmail.blubberalls.custom_events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.custom_events.event.InventoryChangedEvent;
import com.google.common.collect.HashMultimap;

import net.minecraft.world.inventory.ICrafting;

public class CustomInventoryListener implements Listener {
    private static int inventoryChangeSchedulerID = -1;
    private static HashMap<Inventory, ItemStack[]> previousInventories = new HashMap<Inventory, ItemStack[]>();
    private static HashMultimap<Inventory, Integer> changedSlots = HashMultimap.create();
    private static ICrafting nmsSlotListener = new ICrafting() {
        // On slot change
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {                        
            Inventory changedInventory = nmsContainer.getBukkitView().getInventory(rawSlot);
            int slot = nmsContainer.getBukkitView().convertSlot(rawSlot);
            ItemStack previousStack = previousInventories.get(changedInventory)[slot];
            ItemStack newStack = changedInventory.getItem(slot);

            if (areStacksEqual(previousStack, newStack)) return;

            changedSlots.put(changedInventory, nmsContainer.getBukkitView().convertSlot(rawSlot));

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
        for (Inventory changed : changedSlots.keySet()) {
            ItemStack[] previousInventory = previousInventories.get(changed);
            HashMap<Integer, ItemStack> previous = new HashMap<Integer, ItemStack>();

            for (int i : changedSlots.get(changed)) {
                ItemStack previousStack = previousInventory[i];
                ItemStack currentStack = changed.getItem(i);

                previous.put(i, previousStack);
                previousInventory[i] = currentStack != null ? currentStack.clone() : null;
            }

            InventoryChangedEvent updateEvent = new InventoryChangedEvent(changed, previous);

            Bukkit.getPluginManager().callEvent(updateEvent);
        }

        changedSlots.clear();
        inventoryChangeSchedulerID = -1;
    }

    public static int getInventoryChangeSchedulerID() {
        return inventoryChangeSchedulerID;
    }

    public static void registerInventory(Inventory inventory) {
        if (previousInventories.containsKey(inventory)) return;

        Bukkit.getLogger().info("REGISTERED " + inventory.getType());

        ItemStack[] contents = new ItemStack[inventory.getSize()];

        for (int i = 0; i < contents.length; i++) {            
            contents[i] = inventory.getItem(i) != null ? inventory.getItem(i).clone() : null;
        }

        previousInventories.put(inventory, contents);
    }

    public static void registerInventories(InventoryView view) {
        registerInventory(view.getTopInventory());
        registerInventory(view.getBottomInventory());
        
        Bukkit.getLogger().info(" " + previousInventories);
    }

    public static void deregisterInventory(Inventory inventory) {
        previousInventories.remove(inventory);
        changedSlots.removeAll(inventory);
    }

    public static void addNMSSlotListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener (if there)
        craftView.getHandle().b(nmsSlotListener);
        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        registerInventories(event.getPlayer().getOpenInventory());
        addNMSSlotListener(event.getPlayer().getOpenInventory());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        deregisterInventory(event.getPlayer().getInventory());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        registerInventories(event.getView());
        addNMSSlotListener(event.getView());
    }
}
