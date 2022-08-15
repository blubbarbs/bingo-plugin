package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.InventoryUpdateEvent;

public class PickupDiamonds extends Goal {

    @EventHandler
    public void onInventoryChange(InventoryUpdateEvent event) {        
        if (event.getNewItemStack().getType() != Material.DIAMOND) return;

        Bukkit.getLogger().info("DEBT");

        Team t = getTeam((Player) event.getView().getPlayer());
        setTeamCompleted(t);
    }
    
}
