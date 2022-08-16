package com.gmail.blubberalls.custom_events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryUpdateEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();   
   
    private ItemStack[] previousContents;

    public InventoryUpdateEvent(Player p, ItemStack[] previousContents) {
        super(p);

        this.previousContents = previousContents;
    }
 
    public ItemStack[] getPreviousContents() {
        return previousContents;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
