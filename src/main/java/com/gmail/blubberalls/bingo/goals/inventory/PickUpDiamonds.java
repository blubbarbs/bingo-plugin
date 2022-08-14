package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.InventoryGoal;

public class PickUpDiamonds extends InventoryGoal {

    @Override
    public void onInventoryChange(Player player, ItemStack newStack, int changedSlot) {        
        if (newStack.getType() == Material.DIAMOND) {
            setTeamCompleted(player);
        }    
    }
    
}
