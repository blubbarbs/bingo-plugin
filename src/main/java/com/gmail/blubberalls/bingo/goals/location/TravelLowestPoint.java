package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class TravelLowestPoint extends Goal {
    
    @EventHandler
    public void onPlayerExist(PlayerExistEvent event) {
        if (event.getPlayer().getLocation().getY() > event.getPlayer().getWorld().getMinHeight() + 1
        || !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
