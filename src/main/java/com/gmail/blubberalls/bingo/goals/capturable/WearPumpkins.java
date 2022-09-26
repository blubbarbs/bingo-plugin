package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class WearPumpkins extends CapturableGoal {

    @Override
    public String getTitle() {
        return "Lantern Jack Fanclub";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("carved_pumpkin");
    }

    @Override
    public String getDescription() {
        return "Have your entire team wear a Carved Pumpkin.";
    }

    public boolean teamCheck(Team t) {
        return game.getTeamPlayers(t).stream().allMatch(this::hasPumpkin);
    }

    public boolean hasPumpkin(Player p) {
        return p.getInventory().getHelmet() != null 
            && p.getInventory().getHelmet().getType() == Material.CARVED_PUMPKIN;
    }
    
    @EventHandler
    public void onInventoryChange(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;
        
        Team t = game.getTeam(event.getPlayer());

        setCompletedFor(t, willTeamComplete(t));
    }
}
