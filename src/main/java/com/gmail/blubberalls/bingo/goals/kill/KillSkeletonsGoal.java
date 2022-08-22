package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.NumerableGoal;

public class KillSkeletonsGoal extends NumerableGoal {
    
    @Override
    public int getGoal() {
        return 5;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {        
        if (event.getEntity().getType() != EntityType.SKELETON
        ||  event.getEntity().getKiller() == null) return;

        Player p = event.getEntity().getKiller();
        
        if (!game.isPlayerPlaying(p)) return;
        
        addTeamCompletion(game.getTeam(p), 1);
    }

}
