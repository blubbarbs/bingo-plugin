package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillByStalagmite extends Goal {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getLastDamageCause().getCause() != DamageCause.CONTACT
        ||  event.getEntity().getLocation().getBlock().getType() != Material.POINTED_DRIPSTONE
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return; 
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
