package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class PlaceWetSponge extends Goal {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.WET_SPONGE
        ||  event.getBlock().getWorld().getEnvironment() != Environment.NETHER) return;

        setCompletedFor(event.getPlayer());
    }


}
