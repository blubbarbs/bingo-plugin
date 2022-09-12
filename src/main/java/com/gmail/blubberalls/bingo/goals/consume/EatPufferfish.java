package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class EatPufferfish extends Goal {
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getItem().getType() != Material.PUFFERFISH) return;
        
        setCompletedFor(event.getPlayer());
    }
}
