package com.gmail.blubberalls.bingo.goals.kill;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class DieByFallingAnvil extends Goal {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!game.isPlayerPlaying(event.getEntity())
        ||  !(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent lastDamage = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

        if (lastDamage.getDamager().getType() != EntityType.FALLING_BLOCK) return;

        FallingBlock fallingBlock = (FallingBlock) lastDamage.getDamager();

        if (fallingBlock.getBlockData().getMaterial() != Material.ANVIL) return;

        setCompletedFor(event.getEntity());
    }

}
