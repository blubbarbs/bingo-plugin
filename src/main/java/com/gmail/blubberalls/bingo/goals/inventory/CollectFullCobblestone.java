package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.InventoryChangedEvent;

public class CollectFullCobblestone extends Goal {
    
    public boolean hasAllCobblestone(Player p) {
        for (ItemStack i : p.getInventory().getStorageContents()) {
            if (i == null 
            ||  i.getType() != Material.COBBLESTONE 
            ||  i.getAmount() != 64) return false;
        }

        return true;
    }

    @EventHandler
    public void onInventoryChange(InventoryChangedEvent event) {
        if (event.getInventory().getType() != InventoryType.PLAYER) return;

        Player p = (Player) event.getInventory().getHolder();

        if (!hasAllCobblestone(p)) return;

        setCompletedFor(p);
    }
}
