package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillSkeletonsGoal extends Goal {
    
    @Override
    public int getMaximumGoal() {
        return 10;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.SKELETON
            || event.getEntity().getKiller() == null) return;

        addCompletion(event.getEntity().getKiller(), 1);
    }

}
