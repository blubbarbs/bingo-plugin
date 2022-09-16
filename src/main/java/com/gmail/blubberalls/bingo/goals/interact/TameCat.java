package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class TameCat extends Goal {
    @EventHandler
    public void onTame(EntityTameEvent event) {
        if (event.getEntityType() != EntityType.CAT
        ||  !(event.getOwner() instanceof Player)) return;

        Player p = (Player) event.getOwner();

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
