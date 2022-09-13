package com.gmail.blubberalls.bingo.goals.build;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockIgniteEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class BuildHerobrineAltar extends Goal {
    
    @Override
    public String getTitle() {
        return "Summon Herobrine";
    }

    @Override
    public String getIconPath() {
        return "bingo.icons.herobrine";
    }

    public boolean checkAltar(Block netherrackBlock) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (netherrackBlock.getRelative(x, -1, z).getType() != Material.GOLD_BLOCK) {
                    return false;
                }
            }
        }

        if (netherrackBlock.getRelative(BlockFace.NORTH).getType() != Material.REDSTONE_TORCH
        ||  netherrackBlock.getRelative(BlockFace.EAST).getType() != Material.REDSTONE_TORCH
        ||  netherrackBlock.getRelative(BlockFace.SOUTH).getType() != Material.REDSTONE_TORCH
        ||  netherrackBlock.getRelative(BlockFace.WEST).getType() != Material.REDSTONE_TORCH) return false;

        return true;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getBlock().getRelative(BlockFace.DOWN).getType() != Material.NETHERRACK) return;

        Block netherrack = event.getBlock().getRelative(BlockFace.DOWN);
        boolean hasAltar = checkAltar(netherrack);

        if (!hasAltar
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        event.getBlock().getWorld().strikeLightningEffect(event.getBlock().getLocation());
        setCompletedFor(event.getPlayer());
    }

}
