package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class EatMostFood extends ScoredCapturableGoal {

    @Override
    public String getTitle() {
        return "Glutton";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("potato");
    }

    @Override
    public String getDescription() {
        return "Have the most food eaten.";
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().isEdible()
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        addCompletionFor(event.getPlayer(), 1);
    }

}
