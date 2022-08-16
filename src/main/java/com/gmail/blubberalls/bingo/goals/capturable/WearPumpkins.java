package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class WearPumpkins extends CapturableGoal {
    
    public boolean testCondition(Player p) {
        return p.getInventory().getHelmet() != null 
            && p.getInventory().getHelmet().getType() == Material.CARVED_PUMPKIN;
    }
    
    @EventHandler
    public void onInventoryChange(PlayerInventoryChangedEvent event) {
        Player player = event.getPlayer();
        
        if (!game.isPlaying(player)) return;
        
        Team t = game.getTeam(player);
        
        if (shouldComplete(t)) {
            Bukkit.getLogger().info("COMPLETED");
            setTeamCompleted(t);
        }
        else {
            Bukkit.getLogger().info("NOT COMPLETED");
            setTeamCompleted(t, false);
        }
    }
}
