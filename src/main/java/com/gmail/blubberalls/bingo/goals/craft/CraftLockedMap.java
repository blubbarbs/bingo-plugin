package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;

import com.gmail.blubberalls.bingo.goal.Goal;

public class CraftLockedMap extends Goal {
    
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getInventory().getType() == InventoryType.CARTOGRAPHY
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())
        ||  event.getInventory().getMatrix()[1].getType() != Material.GLASS_PANE) return;

        setCompletedFor((Player) event.getWhoClicked());
    }
}
