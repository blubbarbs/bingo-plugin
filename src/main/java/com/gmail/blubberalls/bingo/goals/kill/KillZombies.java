package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class KillZombies extends UniqueKeysGoal {
    // I know I could do instanceof I just don't want to
    public boolean isZombie(Entity e) {
        switch(e.getType()) {
            case ZOMBIE:
            case HUSK:
            case DROWNED:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public int getGoal() {
        return 3;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        return super.getProgressDescriptionFor(t).replaceAll("Unique Keys", "Zombie Types Killed");
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (!isZombie(event.getEntity())
        ||  event.getEntity().getKiller() == null) return;
        
        Player p = event.getEntity().getKiller();

        if (!game.isPlayerPlaying(p)
        ||  containsUniqueKeyFor(p, event.getEntityType())) return;

        addUniqueKeyFor(p, event.getEntityType());
    }
}
