package com.gmail.blubberalls.bingo.goals.fish;

import com.gmail.blubberalls.bingo.goal.Goal;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

public class FishTropicalFish extends Goal {

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() == null
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        || ((Item) event.getCaught()).getItemStack().getType() != Material.TROPICAL_FISH
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }

}
