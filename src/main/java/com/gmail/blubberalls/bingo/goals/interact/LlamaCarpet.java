package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;

import com.gmail.blubberalls.bingo.goal.Goal;

public class LlamaCarpet extends Goal {
    public boolean isCarpet(ItemStack stack) {
        return stack != null && stack.getType().name().endsWith("CARPET");
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!game.isPlayerPlaying(clicker)
        ||  !(event.getView().getTopInventory() instanceof LlamaInventory)
        ||  event.getResult() == Result.DENY) return;

        LlamaInventory llamaInventory = (LlamaInventory) event.getView().getTopInventory();

        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            ItemStack stack = event.getCurrentItem();

            if (!isCarpet(stack)
            ||  llamaInventory.getDecor() != null) return;

            setCompletedFor(clicker);
        }
        else {
            ItemStack stack = event.getCursor();

            if (!isCarpet(stack)) return;

            setCompletedFor(clicker);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!game.isPlayerPlaying(clicker)
        ||  !(event.getView().getTopInventory() instanceof LlamaInventory)
        ||  event.getResult() == Result.DENY
        ||  !event.getNewItems().values().stream().anyMatch(stack -> isCarpet(stack))) return;
    
        setCompletedFor(clicker);
    }
}
