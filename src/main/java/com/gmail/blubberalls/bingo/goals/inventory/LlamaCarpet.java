package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;

public class LlamaCarpet extends Goal {
    public boolean isCarpet(ItemStack stack) {
        return stack != null && stack.getType().name().endsWith("CARPET");
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  !(event.getView().getTopInventory() instanceof LlamaInventory)
        ||  event.getResult() == Result.DENY) return;

        LlamaInventory llamaInventory = (LlamaInventory) event.getView().getTopInventory();

        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;

            ItemStack stack = event.getCurrentItem();

            if (!isCarpet(stack)
            ||  llamaInventory.getDecor() != null
            ||  !game.isPlayerPlaying(clicker)) return;

            setCompletedFor(clicker);
        }
        else {
            if (!Checks.willPlaceInInventory(event.getAction())) return;            
            
            ItemStack stack = event.getCursor();

            if (!isCarpet(stack)
            ||  !game.isPlayerPlaying(clicker)) return;

            setCompletedFor(clicker);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!(event.getView().getTopInventory() instanceof LlamaInventory)
        ||  event.getResult() == Result.DENY
        ||  !event.getNewItems().values().stream().anyMatch(stack -> isCarpet(stack))
        ||  !game.isPlayerPlaying(clicker)) return;
    
        setCompletedFor(clicker);
    }
}
