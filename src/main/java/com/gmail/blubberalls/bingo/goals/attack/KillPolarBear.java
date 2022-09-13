package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillPolarBear extends Goal {
    
    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.POLAR_BEAR
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        setCompletedFor(event.getEntity().getKiller());
    }

}
