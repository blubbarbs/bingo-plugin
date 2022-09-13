package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class ReachLevel30 extends Goal {
    @EventHandler
    public void onLevelUp(PlayerExpChangeEvent event) {
        if (event.getPlayer().getLevel() < 30
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
