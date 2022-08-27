package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class FishTropicalFish extends Goal {

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getCaught().getType() != EntityType.TROPICAL_FISH
        || event.getPlayer().getWorld().getBiome(event.getPlayer().getLocation()) != Biome.LUSH_CAVES) return;

        setCompletedFor(event.getPlayer());
    }
}
