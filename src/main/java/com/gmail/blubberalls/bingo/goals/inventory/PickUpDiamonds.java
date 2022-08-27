package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.InventoryChangedEvent;

public class PickupDiamonds extends Goal {

    @EventHandler
    public void onInventoryChange(InventoryChangedEvent event) {                
        if (event.getInventory().getType() != InventoryType.PLAYER
        ||  !game.isPlayerPlaying((Player) event.getInventory().getHolder())
        ||  !event.getInventory().contains(Material.DIAMOND)) return;

        Player p = (Player) event.getInventory().getHolder();

        setCompletedFor(p);
    }
    
}
