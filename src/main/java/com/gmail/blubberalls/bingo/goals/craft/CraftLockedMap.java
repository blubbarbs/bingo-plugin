package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftLockedMap extends Goal {

    @Override
    public String getTitle() {
        return "Map to Nowhere";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("map");
    }

    @Override
    public String getDescription() {
        return "Craft a Locked Map in a Cartography Table.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getInventory().getType() == InventoryType.CARTOGRAPHY
        ||  event.getInventory().getMatrix()[1].getType() != Material.GLASS_PANE
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        setCompletedFor((Player) event.getWhoClicked());
    }
}
