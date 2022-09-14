package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;

public class CompleteVillagerTrades extends ScoredGoal {
    @Override
    public int getGoal() {
        return 10;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!(event.getView().getTopInventory().getHolder() instanceof Villager)
        ||  event.getSlotType() != SlotType.RESULT
        ||  event.getCurrentItem() == null
        ||  (!event.getAction().name().startsWith("PICKUP") && event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY)
        ||  event.getResult() == Result.DENY
        ||  !game.isPlayerPlaying(clicker)) return;

        addCompletionFor(clicker, 1);
    }

}
