package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemBreakEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class BreakNetheriteHoe extends Goal {
    
    @Override
    public String getTitle() {
        return "Dedicated Farmer";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("netherite_hoe");
    }

    @Override
    public String getDescription() {
        return "Break a Netherite Hoe.";
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        if (event.getBrokenItem().getType() != Material.NETHERITE_HOE
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
    
        setCompletedFor(event.getPlayer());
    }
}
