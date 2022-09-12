package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillByStalagmite extends Goal {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())
        ||  event.getEntity().getLastDamageCause().getCause() != DamageCause.CONTACT
        ||  event.getEntity().getLocation().getBlock().getType() != Material.POINTED_DRIPSTONE) return; 
    
        Player killer = event.getEntity().getKiller();

        setCompletedFor(killer);
    }
}
