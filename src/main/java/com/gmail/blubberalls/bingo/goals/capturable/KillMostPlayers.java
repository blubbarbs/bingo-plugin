package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillMostPlayers extends ScoredCapturableGoal {    

    @Override
    public String getTitle() {
        return "Bloodthirsty";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("iron_sword");
    }

    @Override
    public String getDescription() {
        return "Have the most player kills.";
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!game.isPlayerPlaying(event.getEntity())
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        addCompletionFor(event.getEntity().getKiller(), 1);
    }
}
