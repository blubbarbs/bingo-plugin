package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemBreakEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class BreakNetheriteHoe extends Goal {
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        if (event.getBrokenItem().getType() != Material.NETHERITE_HOE
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
    
        setCompletedFor(event.getPlayer());
    }
}
