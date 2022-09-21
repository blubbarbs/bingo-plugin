package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class PlayerTeleportFar extends Goal {

    @Override
    public String getTitle() {
        return "Long Distance Throw";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("ender_pearl");
    }

    @Override
    public String getDescription() {
        return "Teleport >= 100 blocks with an Ender Pearl.";
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != TeleportCause.ENDER_PEARL) return;

        Location fromNormalizedY = event.getFrom().clone();
        fromNormalizedY.setY(event.getTo().getY());
        double distance = event.getTo().distance(fromNormalizedY);

        if (distance < 100
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
