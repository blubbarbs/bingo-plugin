package com.gmail.blubberalls.custom_events.event;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryChangedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();   

    private Inventory inventory;
    private HashMap<Integer, ItemStack> previous = new HashMap<Integer, ItemStack>();

    public InventoryChangedEvent(Inventory inventory, HashMap<Integer, ItemStack> previous) {
        super();
        
        this.inventory = inventory;
        this.previous = previous;
    }
 
    public Inventory getInventory() {
        return inventory;
    }

    public Set<Integer> getUpdatedSlots() {
        return previous.keySet();
    }

    public ItemStack getPrevious(int slot) {
        return previous.get(slot);
    }

    public HashMap<Integer, ItemStack> getPrevious() {
        return previous;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
