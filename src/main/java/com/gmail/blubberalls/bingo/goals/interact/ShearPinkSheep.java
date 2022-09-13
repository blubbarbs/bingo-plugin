package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class ShearPinkSheep extends Goal {

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        if (event.getEntity().getType() != EntityType.SHEEP) return;
    
        Sheep sheep = (Sheep) event.getEntity();
        
        if (sheep.getColor() != DyeColor.PINK
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }   
}
