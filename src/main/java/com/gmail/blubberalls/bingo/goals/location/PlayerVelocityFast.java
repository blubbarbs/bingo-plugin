package com.gmail.blubberalls.bingo.goals.location;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class PlayerVelocityFast extends Goal {
    private HashMap<Player, Location> previousLocations = new HashMap<Player, Location>();
    
    @Override
    public String getTitle() {
        return "Faster than Fast, Quicker than Quick";
    }

    @Override
    public String getDescription() {
        return "Have an extremely high horizontal velocity.";
    }

    @EventHandler
    public void onExist(PlayerExistEvent event) {
        if (event.getPlayer().isDead()
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        Location previousLocation = previousLocations.getOrDefault(event.getPlayer(), event.getPlayer().getLocation());
        previousLocation.setY(event.getPlayer().getLocation().getY());
        double velocitySquared = previousLocation.distanceSquared(event.getPlayer().getLocation());

        if (velocitySquared > 200) {
            setCompletedFor(event.getPlayer());
        }
        else {
            previousLocations.put(event.getPlayer(), event.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        previousLocations.remove(event.getEntity());
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        previousLocations.remove(event.getPlayer());
    }

    @EventHandler
    public void onDimensionChange(PlayerChangedWorldEvent event) {
        previousLocations.remove(event.getPlayer());
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        previousLocations.remove(event.getPlayer());
    }

    @Override
    public void unload() {
        super.unload();
        previousLocations.clear();
    }

}
