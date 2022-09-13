package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillCookedAnimal extends Goal{

    public boolean isCookable(EntityType type) {
        switch(type) {
            case CHICKEN:
            case COW:
            case PIG:
            case SHEEP:
            case RABBIT:
            case COD:
            case SALMON:
                return true;
            default:
                return false;
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (!isCookable(event.getEntityType())
        ||  event.getEntity().getKiller() == null
        ||  event.getEntity().getFireTicks() <= 0) return;

        setCompletedFor(event.getEntity().getKiller());
    }
}
