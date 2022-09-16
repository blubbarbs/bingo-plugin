package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;
import com.gmail.blubberalls.bingo.util.Checks;

public class CompleteVillagerTrades extends ScoredGoal {
    @Override
    public int getGoal() {
        return 10;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  !(event.getClickedInventory().getHolder() instanceof Villager)
        ||  event.getSlotType() != SlotType.RESULT
        ||  event.getCurrentItem() == null
        ||  !Checks.willTakeFromInventory(event.getAction())
        ||  event.getResult() == Result.DENY
        ||  !game.isPlayerPlaying(clicker)) return;

        addCompletionFor(clicker, 1);
    }

}
