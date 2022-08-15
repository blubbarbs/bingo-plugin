package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.InventoryUpdateEvent;

public class PickUpDiamonds extends Goal {

    @EventHandler
    public void onInventoryChange(InventoryUpdateEvent event) {        
        Bukkit.getLogger().info("EVENT TRIGGERED");

        if (event.getNewItemStack().getType() == Material.DIAMOND) {
            setTeamCompleted((Player) event.getInventory().getHolder());
        }    
    }
    
}
