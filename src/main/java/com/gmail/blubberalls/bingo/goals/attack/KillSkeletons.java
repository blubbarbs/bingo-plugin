package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Keyed;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillSkeletons extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Skeleton Tour";
    }

    @Override
    public String getIconPath() {
        return Icons.MISC("straw_hat_pirates");
    }

    @Override
    public String getDescription() {
        return "Kill each type of Skeleton.";
    }

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
