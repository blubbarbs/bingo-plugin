package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class AttackPlayerWithFishingRod extends Goal {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER
        ||  event.getDamager().getType() != EntityType.FISHING_HOOK) return;
    
        Player p = (Player) event.getEntity();
        FishHook hook = (FishHook) event.getDamager();
    
        if (!(hook.getShooter() instanceof Player)) return;

        Player shooter = (Player) hook.getShooter();

        if (!game.isPlayerPlaying(shooter)
        ||  game.getTeam(p).equals(game.getTeam(shooter))) return;
    
        setCompletedFor(shooter);
    }
}
