package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class MineAmethystCrystal extends Goal {
    
    @EventHandler
    public void onMine(BlockDropItemEvent event) {
        if (event.getBlockState().getType() != Material.AMETHYST_BLOCK
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
