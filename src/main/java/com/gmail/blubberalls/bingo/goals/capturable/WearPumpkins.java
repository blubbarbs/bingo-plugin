package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.custom_events.event.InventoryChangedEvent;

public class WearPumpkins extends CapturableGoal {

    public boolean hasPumpkin(Player p) {
        return p.getInventory().getHelmet() != null 
            && p.getInventory().getHelmet().getType() == Material.CARVED_PUMPKIN;
    }
    
    @EventHandler
    public void onInventoryChange(InventoryChangedEvent event) {
        if (event.getInventory().getType() != InventoryType.PLAYER
            || !game.isPlayerPlaying((Player) event.getInventory().getHolder())) return;
        
        Player player = (Player) event.getInventory().getHolder();
        Team t = game.getTeam(player);

        setCompletedFor(t, game.allTeamMembersMatchCondition(t, this::hasPumpkin));
    }
}
