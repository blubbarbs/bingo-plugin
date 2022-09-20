package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterLushCave extends Goal {

    @Override
    public String getTitle() {
        return "Underground Lush";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("flowering_azalea_leaves");
    }

    @Override
    public String getDescription() {
        return "Enter a Lush Cave";
    }

    @EventHandler
    public void onExist(PlayerExistEvent event) {
        if (event.getPlayer().getWorld().getBiome(event.getPlayer().getLocation()) != Biome.LUSH_CAVES
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
