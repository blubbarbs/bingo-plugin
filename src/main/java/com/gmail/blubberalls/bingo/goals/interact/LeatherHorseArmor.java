package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;

public class LeatherHorseArmor extends Goal {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!game.isPlayerPlaying(clicker)
        ||  !(event.getView().getTopInventory() instanceof HorseInventory)
        ||  event.getResult() == Result.DENY) return;

        HorseInventory horseInventory = (HorseInventory) event.getView().getTopInventory();

        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            ItemStack stack = event.getCurrentItem();

            if (stack.getType() != Material.LEATHER_HORSE_ARMOR
            ||  horseInventory.getArmor() != null) return;

            setCompletedFor(clicker);
        }
        else {
            ItemStack stack = event.getCursor();

            if (stack.getType() != Material.LEATHER_HORSE_ARMOR) return;

            setCompletedFor(clicker);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!game.isPlayerPlaying(clicker)
        ||  !(event.getView().getTopInventory() instanceof HorseInventory)
        ||  event.getResult() == Result.DENY
        ||  !event.getNewItems().values().stream().anyMatch(stack -> stack.getType() == Material.LEATHER_HORSE_ARMOR)) return;
    
        setCompletedFor(clicker);
    }
}
