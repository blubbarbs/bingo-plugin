package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFertilizeEvent;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;

public class FertilizeCrops extends ScoredGoal {
    @Override
    public int getGoal() {
        return 64;
    }

    @EventHandler
    public void onFertilize(BlockFertilizeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        addCompletionFor(event.getPlayer(), 1);
    }

}
