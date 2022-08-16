package com.gmail.blubberalls.custom_events.event;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryChangedEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();   
   
    private HashMap<Integer, ItemStack> previous = new HashMap<Integer, ItemStack>();

    public PlayerInventoryChangedEvent(Player p, HashMap<Integer, ItemStack> previous) {
        super(p);

        this.previous = previous;
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
