package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class ParrotTame extends Goal {
    @EventHandler
    public void onTame(EntityTameEvent event) {
        if (event.getEntityType() != EntityType.PARROT
        ||  Bukkit.getPlayer(event.getOwner().getUniqueId()) == null) return;
    
        Player p = Bukkit.getPlayer(event.getOwner().getUniqueId());

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
