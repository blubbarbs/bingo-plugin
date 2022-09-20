package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class TradeWithPiglins extends ScoredGoal {    

    @Override
    public String getTitle() {
        return "Shady Salesperson";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("piglin");
    }

    @Override
    public String getDescription() {
        return "Begin 18 Trades with Piglins.";
    }

    @Override
    public int getGoal() {
        return 18;
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.PIGLIN
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.GOLD_INGOT) return;

        Piglin piglin = (Piglin) event.getRightClicked();

        if (piglin.getInventory().getItem(0) != null
        ||  !piglin.isAdult()
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        addCompletionFor(event.getPlayer(), 1);

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
