package com.gmail.blubberalls.bingo.goals.inventory;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class UseSmoker extends Goal {
    
    @Override
    public String getTitle() {
        return "Smokey";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("smoker");
    }

    @Override
    public String getDescription() {
        return "Use a Smoker.";
    }    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  event.getClickedInventory().getType() != InventoryType.SMOKER
        ||  event.getSlotType() != SlotType.RESULT
        ||  event.getCurrentItem() == null
        ||  !Checks.willTakeFromInventory(event.getAction())
        ||  event.getResult() == Result.DENY
        ||  !game.isPlayerPlaying(clicker)) return;

        setCompletedFor(clicker);
    }
}
