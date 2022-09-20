package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;

public class SmeltSmoothStone extends Goal {
    
    @Override
    public String getTitle() {
        return "Smooth";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("smooth_stone");
    }

    @Override
    public String getDescription() {
        return "Smelt Smooth Stone.";
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  event.getClickedInventory().getType() != InventoryType.FURNACE
        ||  event.getSlotType() != SlotType.RESULT
        ||  event.getCurrentItem() == null
        ||  event.getCurrentItem().getType() != Material.SMOOTH_STONE
        ||  !Checks.willTakeFromInventory(event.getAction())
        ||  event.getResult() == Result.DENY
        ||  !game.isPlayerPlaying(clicker)) return;

        setCompletedFor(clicker);
    }
}
