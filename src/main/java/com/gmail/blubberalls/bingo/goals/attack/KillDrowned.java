package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillDrowned extends Goal {
    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.DROWNED
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent lastDamage = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

        if (lastDamage.getDamager().getType() != EntityType.TRIDENT
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;

        setCompletedFor(event.getEntity().getKiller());
    }
}
