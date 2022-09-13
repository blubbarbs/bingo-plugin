package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

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
    public void onInventoryChange(PlayerInventoryChangedEvent event) {
        if (!hasAllCobblestone(event.getPlayer())
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
