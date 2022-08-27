package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillPhantom extends Goal {
    
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.DROWNED
        ||  event.getEntity().getKiller() == null
        ||  event.getEntity().getLastDamageCause().getCause() != DamageCause.PROJECTILE) return;
        
        Player killer = event.getEntity().getKiller(); 
        EntityDamageByEntityEvent lastDamageEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

        if (!game.isPlayerPlaying(killer)
        ||  lastDamageEvent.getDamager().getType() != EntityType.TRIDENT) return;

        setCompletedFor(game.getTeam(killer));
    }
}
