package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class FishTripwireHook extends Goal {

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        || ((Item) event.getCaught()).getItemStack().getType() != Material.TRIPWIRE_HOOK
        || event.getPlayer().getWorld().getBiome(event.getPlayer().getLocation()) != Biome.JUNGLE) return;

        setCompletedFor(event.getPlayer());
    }
}
