package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillBoA extends Goal {
    
    @Override
    public String getTitle() {
        return "Shell Shocker";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("spider_eye");
    }

    @Override
    public String getDescription() {
        return "Kill an Arthropod mob with the Bane of Arthropods enchantment.";
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getCategory() != EntityCategory.ARTHROPOD
        ||  event.getEntity().getKiller() == null
        ||  event.getEntity().getLastDamageCause().getCause() != DamageCause.ENTITY_ATTACK
        ||  event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS) == 0
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
