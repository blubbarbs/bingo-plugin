package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class TravelHighestPoint extends Goal {
    
    @EventHandler
    public void onPlayerExist(PlayerExistEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getPlayer().getLocation().getY() < event.getPlayer().getWorld().getMaxHeight() - 1) return;
        
        setCompletedFor(event.getPlayer());
    }
}
