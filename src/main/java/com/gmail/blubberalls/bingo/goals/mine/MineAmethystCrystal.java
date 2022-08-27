package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class MineAmethystCrystal extends Goal {
    
    @EventHandler
    public void onMine(BlockDropItemEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  (event.getBlock().getType() != Material.AMETHYST_BLOCK 
            &&  event.getBlock().getType() != Material.AMETHYST_SHARD 
            &&  event.getBlock().getType() != Material.AMETHYST_CLUSTER)) return;

        setCompletedFor(event.getPlayer());
    }
}
