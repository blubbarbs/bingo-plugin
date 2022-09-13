package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillPhantom extends Goal {
    
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.DROWNED
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        
        EntityDamageByEntityEvent lastDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

        if (lastDamageEvent.getDamager().getType() != EntityType.SPECTRAL_ARROW
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        setCompletedFor(game.getTeam(event.getEntity().getKiller()));
    }
}
