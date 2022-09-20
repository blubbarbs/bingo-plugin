package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillPhantom extends Goal {
    
    @Override
    public String getTitle() {
        return "Something";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("spectral_arrow");
    }

    @Override
    public String getDescription() {
        return "Kill a Phantom with a Spectral Arrow";
    }

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
