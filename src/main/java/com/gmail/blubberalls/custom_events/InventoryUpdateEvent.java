package com.gmail.blubberalls.custom_events;

import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryUpdateEvent extends InventoryEvent {
    private static final HandlerList handlers = new HandlerList();   
   
    private ItemStack stack;
    private int slot;

    public InventoryUpdateEvent(InventoryView transaction, ItemStack stack, int slot) {
        super(transaction);

        this.stack = stack;
        this.slot = slot;
    }
 
    public ItemStack getNewItemStack() {
        return stack;
    }

    public int getChangedSlot() {
        return slot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
