package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;

public class KillMostPlayers extends ScoredCapturableGoal {    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!game.isPlayerPlaying(event.getEntity().getKiller())) return;

        addCompletionFor(event.getEntity().getKiller(), 1);
    }
}
