package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class PlaceWetSponge extends Goal {
    
    @Override
    public String getTitle() {
        return "Hot Sponge";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("wet_sponge");
    }

    @Override
    public String getDescription() {
        return "Place a Wet Sponge in the Nether.";
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.WET_SPONGE
        ||  event.getBlock().getWorld().getEnvironment() != Environment.NETHER) return;

        setCompletedFor(event.getPlayer());
    }

}
