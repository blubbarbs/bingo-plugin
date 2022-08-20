package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.NumerableGoal;

public class KillCreepersGoal extends NumerableGoal {
    
    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {        
        if (event.getEntity().getType() != EntityType.CREEPER
            || event.getEntity().getKiller() == null) return;

        Player p = event.getEntity().getKiller();
        
        if (!game.isPlaying(p)) return;
        
        addTeamCompletion(game.getTeam(p), 1);
    }

}
