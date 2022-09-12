package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.Keyed;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class KillZombies extends UniqueKeysGoal {

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.DROWNED,
            EntityType.HUSK,
            EntityType.ZOMBIFIED_PIGLIN
        };
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        
        Player p = event.getEntity().getKiller();

        if (!game.isPlayerPlaying(p)) return;

        addUniqueKeyFor(p, event.getEntityType());
    }

}
