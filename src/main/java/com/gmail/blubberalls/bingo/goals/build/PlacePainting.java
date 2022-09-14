package com.gmail.blubberalls.bingo.goals.build;

import org.bukkit.Art;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.hanging.HangingPlaceEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class PlacePainting extends Goal {
    @EventHandler
    public void onHang(HangingPlaceEvent event) {
        if (event.getEntity().getType() != EntityType.PAINTING) return;

        Painting painting = (Painting) event.getEntity();
   
        if (painting.getArt() != Art.DONKEY_KONG
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
