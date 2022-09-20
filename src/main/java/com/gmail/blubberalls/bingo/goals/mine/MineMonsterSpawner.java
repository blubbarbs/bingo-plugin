package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class MineMonsterSpawner extends Goal {

    @Override
    public String getTitle() {
        return "Do the Monster Smash";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("spawner");
    }

    @Override
    public String getDescription() {
        return "Mine a Spawner.";
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
