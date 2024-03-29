package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class IgniteTNTFlamingArrow extends Goal {
    
    @Override
    public String getTitle() {
        return "Flamingrobin";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("arrow");
    }

    @Override
    public String getDescription() {
        return "Ignite TNT with a flaming arrow.";
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getHitBlock() == null
        ||  event.getHitBlock().getType() != Material.TNT
        ||  event.getEntity().getType() != EntityType.ARROW
        ||  event.getEntity().getFireTicks() == 0
        ||  !(event.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) event.getEntity().getShooter();
        
        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
