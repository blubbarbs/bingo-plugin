package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class MineAmethystCrystal extends Goal {
    
    @Override
    public String getTitle() {
        return "Amethyst";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("amethyst_shard");
    }

    @Override
    public String getDescription() {
        return "Mine Amethyst from an Amethyst Geode.";
    }

    @EventHandler
    public void onMine(BlockDropItemEvent event) {
        if (event.getBlockState().getType() != Material.AMETHYST_BLOCK
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
