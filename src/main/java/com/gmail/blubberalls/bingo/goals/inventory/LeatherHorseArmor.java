package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;

public class LeatherHorseArmor extends Goal {

    @Override
    public String getTitle() {
        return "Leatherface";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("leather_horse_armor");
    }

    @Override
    public String getDescription() {
        return "Equip a Horse with Leather Horse Armor.";
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  !(event.getView().getTopInventory() instanceof HorseInventory)
        ||  event.getResult() == Result.DENY) return;

        HorseInventory horseInventory = (HorseInventory) event.getView().getTopInventory();

        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;
            
            ItemStack stack = event.getCurrentItem();

            if (stack.getType() != Material.LEATHER_HORSE_ARMOR
            ||  horseInventory.getArmor() != null
            ||  !game.isPlayerPlaying(clicker)) return;

            setCompletedFor(clicker);
        }
        else {
            if (!Checks.willPlaceInInventory(event.getAction())) return;

            ItemStack stack = event.getCursor();

            if (stack.getType() != Material.LEATHER_HORSE_ARMOR
            ||  !game.isPlayerPlaying(clicker)) return;

            setCompletedFor(clicker);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (!(event.getView().getTopInventory() instanceof HorseInventory)
        ||  event.getResult() == Result.DENY
        ||  !event.getNewItems().values().stream().anyMatch(stack -> stack.getType() == Material.LEATHER_HORSE_ARMOR)
        ||  !game.isPlayerPlaying(clicker)) return;
    
        setCompletedFor(clicker);
    }
}
