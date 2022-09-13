package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

import com.gmail.blubberalls.bingo.goal.Goal;

@SuppressWarnings("deprecation")
public class KillBoA extends Goal {
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getCategory() != EntityCategory.ARTHROPOD
        ||  event.getEntity().getKiller() == null
        ||  event.getEntity().getLastDamageCause().getDamage(DamageModifier.BASE) == 0
        ||  event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS) == 0
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
        setCompletedFor(event.getEntity().getKiller());
    }
}
