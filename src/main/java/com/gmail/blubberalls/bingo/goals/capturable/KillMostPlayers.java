package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;

public class KillMostPlayers extends ScoredCapturableGoal {    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player killer = (Player) event.getEntity().getKiller();

        if (!game.isPlayerPlaying(killer)) return;

        addCompletionFor(killer, 1);
    }
}
