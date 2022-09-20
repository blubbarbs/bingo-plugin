package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class TravelHighestPoint extends Goal {

    @Override
    public String getTitle() {
        return "The Summit";
    }

    @Override
    public String getIconPath() {
        return Icons.MISC("high");
    }

    @Override
    public String getDescription() {
        return "Go to the highest point possible in the world.";
    }

    @EventHandler
    public void onPlayerExist(PlayerExistEvent event) {
        if (event.getPlayer().getLocation().getY() < event.getPlayer().getWorld().getMaxHeight() - 1
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
