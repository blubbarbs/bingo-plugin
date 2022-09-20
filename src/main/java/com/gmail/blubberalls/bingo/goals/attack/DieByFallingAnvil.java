package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class DieByFallingAnvil extends Goal {

    @Override
    public String getTitle() {
        return "Act of ACME";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("anvil");
    }

    @Override
    public String getDescription() {
        return "Die by a falling anvil.";
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent lastDamage = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();

        if (lastDamage.getDamager().getType() != EntityType.FALLING_BLOCK) return;

        FallingBlock fallingBlock = (FallingBlock) lastDamage.getDamager();

        if (fallingBlock.getBlockData().getMaterial() != Material.ANVIL
        ||  !game.isPlayerPlaying(event.getEntity())) return;

        setCompletedFor(event.getEntity());
    }

}
