package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillPolarBear extends Goal {

    @Override
    public String getTitle() {
        return "Saxton Hale";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("polar_bear");
    }

    @Override
    public String getDescription() {
        return "Kill a Polar Bear.";
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.POLAR_BEAR
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        setCompletedFor(event.getEntity().getKiller());
    }

}
