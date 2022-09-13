package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;

public class MineMostBlocks extends ScoredCapturableGoal {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        addCompletionFor(event.getPlayer(), 1);
    }    
}
