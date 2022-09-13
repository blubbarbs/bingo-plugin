package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Keyed;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class KillSkeletons extends UniqueKeysGoal {

    public Keyed[] getValidKeys() {
        return new Keyed[] {
            EntityType.SKELETON,
            EntityType.WITHER_SKELETON,
            EntityType.STRAY
        };
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {        
        if (event.getEntity().getKiller() == null
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
        
        addUniqueKeyFor(event.getEntity().getKiller(), event.getEntityType());
    }

}
