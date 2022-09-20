package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class MineMostBlocks extends ScoredCapturableGoal {

    @Override
    public String getTitle() {
        return "Fine Mining";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("iron_pickaxe");
    }

    @Override
    public String getDescription() {
        return "Have the most blocks mined.";
    }    

    @EventHandler
    public void onBlockDrop(BlockBreakEvent event) {
        if (!event.isDropItems()
        ||  event.getBlock().getType().getHardness() == 0
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        addCompletionFor(event.getPlayer(), 1);
    }
}
