package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class FishFishingRod extends Goal {
    
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        || ((Item) event.getCaught()).getItemStack().getType() != Material.FISHING_ROD) return;

        setCompletedFor(event.getPlayer());
    }
}
