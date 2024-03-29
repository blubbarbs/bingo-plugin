package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillZoglin extends Goal {
    
    @Override
    public String getTitle() {
        return "Buhara's Request";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("zoglin");
    }

    @Override
    public String getDescription() {
        return "Kill a Zoglin.";
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOGLIN
        ||  event.getEntity().getKiller() == null) return;
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
