package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class EatGoldenCarrot extends Goal {
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.GOLDEN_CARROT
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
