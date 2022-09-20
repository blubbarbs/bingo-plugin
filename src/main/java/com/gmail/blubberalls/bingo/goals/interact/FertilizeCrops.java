package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFertilizeEvent;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class FertilizeCrops extends ScoredGoal {

    @Override
    public String getTitle() {
        return "Green Thumb";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("bone_meal");
    }

    @Override
    public String getDescription() {
        return "Use Bone Meal 64 times.";
    }

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
