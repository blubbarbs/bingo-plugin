package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class MineGrass extends Goal {
    @Override
    public String getTitle() {
        return "Touch Grass";
    }

    @EventHandler
    public void onBlockMine(BlockDropItemEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
            ||  !event.getItems().stream().anyMatch(item -> item.getItemStack().getType() == Material.GRASS_BLOCK)) return;
    
        setCompletedFor(event.getPlayer());
    }
}
