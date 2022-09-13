package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class MineMonsterSpawner extends Goal {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER
        ||  event.getExpToDrop() == 0
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
