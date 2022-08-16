package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class PickupDiamonds extends Goal {

    @EventHandler
    public void onInventoryChange(PlayerInventoryChangedEvent event) {                
        if (!game.isPlaying((Player) event.getPlayer())
            || !event.getPlayer().getInventory().contains(Material.DIAMOND)) return;

        Team t = game.getTeam(event.getPlayer());
        setTeamCompleted(t);
    }
    
}
