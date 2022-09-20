package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class SuicideByArrow extends Goal {

    @Override
    public String getTitle() {
        return "Self Kebab";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("bow");
    }

    @Override
    public String getDescription() {
        return "Kill yourself with an arrow.";
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        
        EntityDamageByEntityEvent lastDamage = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        
        if (lastDamage.getDamager().getType() != EntityType.ARROW) return;

        Arrow arrow = (Arrow) lastDamage.getDamager();

        if (!event.getEntity().equals(arrow.getShooter())
        ||  !game.isPlayerPlaying(event.getEntity())) return;

        setCompletedFor(event.getEntity());
    }
}
