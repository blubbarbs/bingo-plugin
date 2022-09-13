package com.gmail.blubberalls.bingo.goals.attack;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class KillCreeperWCreeper extends Goal {
    HashMap<Entity, Player> fuse = new HashMap<Entity, Player>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getRightClicked().getType() != EntityType.CREEPER
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.FLINT_AND_STEEL) return;

        Creeper creeper = (Creeper) event.getRightClicked();

        if (fuse.containsKey(creeper)) return;

        fuse.put(creeper, event.getPlayer());
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.CREEPER 
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent lastDamageCause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        
        if (lastDamageCause.getDamager().getType() != EntityType.CREEPER
        ||  !fuse.containsKey(lastDamageCause.getDamager())) return;
    
        Player killer = fuse.get(lastDamageCause.getDamager());
    
        setCompletedFor(killer);
        fuse.remove(lastDamageCause.getDamager());
    }

    @Override
    public void unload() {
        super.unload();
        fuse.clear();
    }
}
