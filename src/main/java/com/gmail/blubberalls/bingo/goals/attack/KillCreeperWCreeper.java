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
import com.gmail.blubberalls.bingo.util.Icons;

public class KillCreeperWCreeper extends Goal {
    HashMap<Entity, Player> fuse = new HashMap<Entity, Player>();

    @Override
    public String getTitle() {
        return "Revenge";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("creeper");
    }

    @Override
    public String getDescription() {
        return "Kill a creeper by exploding another creeper (right click one with flint and steel to make it explode).";
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.CREEPER
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.FLINT_AND_STEEL) return;

        Creeper creeper = (Creeper) event.getRightClicked();

        if (fuse.containsKey(creeper)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

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
    
        if (!game.isPlayerPlaying(killer)) return;

        setCompletedFor(killer);
        fuse.remove(lastDamageCause.getDamager());
    }

    @Override
    public void unload() {
        super.unload();
        fuse.clear();
    }
}
