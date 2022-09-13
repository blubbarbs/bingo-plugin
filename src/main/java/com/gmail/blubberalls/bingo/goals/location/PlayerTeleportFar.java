package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.blubberalls.bingo.goal.Goal;

public class PlayerTeleportFar extends Goal {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != TeleportCause.ENDER_PEARL) return;

        Location fromNormalizedY = event.getFrom().clone();
        fromNormalizedY.setY(event.getTo().getY());
        double distance = event.getTo().distance(fromNormalizedY);

        Bukkit.getLogger().info("distance: " + distance);

        if (distance < 100
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
