package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class TameCat extends Goal {
    
    @Override
    public String getTitle() {
        return "Catatoing";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("red_cat");
    }

    @Override
    public String getDescription() {
        return "Tame a Cat.";
    }

    @EventHandler
    public void onTame(EntityTameEvent event) {
        if (event.getEntityType() != EntityType.CAT
        ||  !(event.getOwner() instanceof Player)) return;

        Player p = (Player) event.getOwner();

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
