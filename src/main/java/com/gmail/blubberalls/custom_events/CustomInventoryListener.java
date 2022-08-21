package com.gmail.blubberalls.custom_events;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.event.InventoryChangedEvent;
import com.google.common.collect.HashMultimap;

import net.minecraft.world.inventory.ICrafting;

public class CustomInventoryListener implements Listener {
    private int inventoryChangeSchedulerID = -1;
    private HashSet<InventoryView> registeredInventoryViews = new HashSet<InventoryView>();
    private HashMap<Inventory, ItemStack[]> previousInventories = new HashMap<Inventory, ItemStack[]>();
    private HashMultimap<Inventory, Integer> changedSlots = HashMultimap.create();
    private ICrafting nmsSlotListener = new ICrafting() {
        // On slot change
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {                        
            if (!Bingo.getInstance().isEnabled()) return;
            
            Inventory changedInventory = nmsContainer.getBukkitView().getInventory(rawSlot);
            int slot = nmsContainer.getBukkitView().convertSlot(rawSlot);
            ItemStack previousStack = previousInventories.get(changedInventory)[slot];
            ItemStack newStack = changedInventory.getItem(slot);

            if (Checks.areItemStacksEqual(previousStack, newStack)) return;

            changedSlots.put(changedInventory, nmsContainer.getBukkitView().convertSlot(rawSlot));
            markInventoriesChanged();
        }

        // On data change (unused)
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, int data) {}
    };

    private void markInventoriesChanged() {
        if (inventoryChangeSchedulerID != -1) return; 

        inventoryChangeSchedulerID = Bukkit.getScheduler().scheduleSyncDelayedTask(Bingo.getInstance(), this::callInventoryChangeEvents);        
    }

    private void callInventoryChangeEvents() {
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

    private void registerInventory(Inventory inventory) {
        if (previousInventories.containsKey(inventory)) return;

        ItemStack[] contents = new ItemStack[inventory.getSize()];

        for (int i = 0; i < contents.length; i++) {            
            contents[i] = inventory.getItem(i) != null ? inventory.getItem(i).clone() : null;
        }

        previousInventories.put(inventory, contents);
    }

    private void deregisterInventory(Inventory inventory) {
        previousInventories.remove(inventory);
        changedSlots.removeAll(inventory);
    }

    private void addNMSSlotListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }

    private void removeNMSSlotListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener (if there)
        craftView.getHandle().b(nmsSlotListener);
    }

    public int getInventoryChangeSchedulerID() {
        return inventoryChangeSchedulerID;
    }

    public void registerInventoryView(InventoryView view) {
        if (registeredInventoryViews.contains(view)) return;

        registerInventory(view.getTopInventory());
        registerInventory(view.getBottomInventory());
        addNMSSlotListener(view);
        registeredInventoryViews.add(view);
    }

    public void deregisterInventoryView(InventoryView view) {
        deregisterInventory(view.getBottomInventory());
        deregisterInventory(view.getTopInventory());
        removeNMSSlotListener(view);
        registeredInventoryViews.remove(view);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        Bukkit.getOnlinePlayers().forEach(p -> registerInventoryView(p.getOpenInventory()));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        registerInventoryView(event.getPlayer().getOpenInventory());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        deregisterInventoryView(event.getPlayer().getOpenInventory());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        registerInventoryView(event.getView());
    }
}
