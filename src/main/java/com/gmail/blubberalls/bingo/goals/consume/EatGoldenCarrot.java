package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class EatGoldenCarrot extends Goal {
    
    @Override
    public String getTitle() {
        return "$20/$20 Vision";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("golden_carrot");
    }

    @Override
    public String getDescription() {
        return "Eat a Golden Carrot.";
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.GOLDEN_CARROT
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
