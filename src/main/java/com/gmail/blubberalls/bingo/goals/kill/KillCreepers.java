package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.NumerableGoal;

public class KillCreepers extends NumerableGoal {
    @Override
    public int getGoal() {
        return 10;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {        
        if (event.getEntityType() != EntityType.CREEPER
        ||  event.getEntity().getKiller() == null) return;

        Player p = event.getEntity().getKiller();
        
        if (!game.isPlayerPlaying(p)) return;
        
        addCompletionFor(p, 1);
    }

}