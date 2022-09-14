package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class SuicideByArrow extends Goal {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getEntity().getKiller().equals(event.getEntity())
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        
        EntityDamageByEntityEvent lastDamage = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        
        if (lastDamage.getDamager().getType() != EntityType.ARROW
        ||  !game.isPlayerPlaying(event.getEntity())) return;

        setCompletedFor(event.getEntity());
    }
}
