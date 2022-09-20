package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillEndermite extends Goal {

    @Override
    public String getTitle() {
        return "I Hardly Know Her!";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("ender_pearl");
    }

    @Override
    public String getDescription() {
        return "Kill an Endermite.";
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ENDERMITE
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
