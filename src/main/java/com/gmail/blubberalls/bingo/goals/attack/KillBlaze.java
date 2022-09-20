package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillBlaze extends Goal {

    @Override
    public String getTitle() {
        return "Snowball Blaze";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("blaze");
    }

    @Override
    public String getDescription() {
        return "Kill a Blaze with a snowball.";
    }

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
