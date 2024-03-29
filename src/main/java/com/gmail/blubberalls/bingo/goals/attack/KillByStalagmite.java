package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillByStalagmite extends Goal {
    
    @Override
    public String getTitle() {
        return "Nailed and Impaled";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("stalagmite");
    }

    @Override
    public String getDescription() {
        return "Kill by impaling on a pointed dripstone.";
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getLastDamageCause().getCause() != DamageCause.CONTACT
        ||  event.getEntity().getLocation().getBlock().getType() != Material.POINTED_DRIPSTONE
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return; 
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
