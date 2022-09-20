package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class KillZombieWithShovel extends Goal {
   
    @Override
    public String getTitle() {
        return "Re-burial";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("zombie");
    }

    @Override
    public String getDescription() {
        return "Kill a Zombie with a shovel.";
    }
    
    public boolean isShovel(ItemStack stack) {
        if (stack == null) return false;

        switch(stack.getType()) {
            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case GOLDEN_SHOVEL:
            case IRON_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:
                return true;
            default:
                return false;
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ZOMBIE
        ||  event.getEntity().getLastDamageCause().getCause() != DamageCause.ENTITY_ATTACK
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())
        ||  !isShovel(event.getEntity().getKiller().getInventory().getItemInMainHand())) return;

        setCompletedFor(event.getEntity().getKiller());
    }
}
