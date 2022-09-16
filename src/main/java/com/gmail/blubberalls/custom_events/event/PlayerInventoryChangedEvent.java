package com.gmail.blubberalls.custom_events.event;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryChangedEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();   

    private HashMap<Integer, ItemStack> previous;
    private HashMap<Integer, ItemStack> current = new HashMap<Integer, ItemStack>();

    public PlayerInventoryChangedEvent(Player player, HashMap<Integer, ItemStack> previous) {
        super(player);
        
        this.previous = previous;
        
        for (int i : previous.keySet()) {
            ItemStack stack = player.getInventory().getItem(i);

            current.put(i, stack != null ? stack : new ItemStack(Material.AIR));
        }
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

    public HashMap<Integer, ItemStack> getCurrent() {
        return current;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
