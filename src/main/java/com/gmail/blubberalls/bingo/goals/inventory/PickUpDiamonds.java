package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class PickupDiamonds extends Goal {

    @EventHandler
    public void onInventoryChange(PlayerInventoryChangedEvent event) {                
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !event.getPlayer().getInventory().contains(Material.DIAMOND)) return;

        setCompletedFor(event.getPlayer());
    }
    
}
