package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillBlaze extends Goal {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.BLAZE
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent lastDamageCause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        
        if (lastDamageCause.getDamager().getType() != EntityType.SNOWBALL
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
