package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterLushCave extends Goal {

    @EventHandler
    public void onExist(PlayerExistEvent event) {
        if (event.getPlayer().getWorld().getBiome(event.getPlayer().getLocation()) != Biome.LUSH_CAVES
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
