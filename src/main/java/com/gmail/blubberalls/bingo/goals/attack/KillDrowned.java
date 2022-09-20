package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillDrowned extends Goal {
    
    @Override
    public String getTitle() {
        return "Neptune's Drowned";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("drowned");
    }

    @Override
    public String getDescription() {
        return "Kill a Drowned with a Trident.";
    }    
    
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
