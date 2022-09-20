package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;

public class TradeWithPiglins extends ScoredGoal {    
    @Override
    public int getGoal() {
        return 18;
    }
    
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntityType() != EntityType.PIGLIN
        ||  event.getItem().getItemStack().getType() != Material.GOLD_INGOT) return;

        Piglin piglin = (Piglin) event.getEntity();
        Player thrower = Bukkit.getPlayer(event.getItem().getThrower());

        if (!piglin.isAdult()
        ||  !game.isPlayerPlaying(thrower)) return;

        addCompletionFor(thrower, 1);
    }
}
