package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillCookedAnimal extends Goal{

    @Override
    public String getTitle() {
        return "Hot BBQ";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("cooked_beef");
    }

    @Override
    public String getDescription() {
        return "Cook a mob (chicken, cow, pig, sheep, rabbit, cod, or salmon) by killing it while it is on fire.";
    }

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
        ||  event.getEntity().getFireTicks() <= 0
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        setCompletedFor(event.getEntity().getKiller());
    }
}
