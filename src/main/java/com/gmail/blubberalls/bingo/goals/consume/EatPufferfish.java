package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class EatPufferfish extends Goal {
    
    @Override
    public String getTitle() {
        return "Puff Puff";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("pufferfish");
    }

    @Override
    public String getDescription() {
        return "Eat a Pufferfish.";
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.PUFFERFISH
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
