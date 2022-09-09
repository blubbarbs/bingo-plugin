package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;

public class EatMostFood extends ScoredCapturableGoal {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !event.getItem().getType().isEdible()) return;

        addCompletionFor(event.getPlayer(), 1);
    }

}
