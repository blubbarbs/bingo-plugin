package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class ReachLevel30 extends Goal {
    @EventHandler
    public void onLevelUp(PlayerExpChangeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getPlayer().getLevel() < 30) return;

        setCompletedFor(event.getPlayer());
    }
}
