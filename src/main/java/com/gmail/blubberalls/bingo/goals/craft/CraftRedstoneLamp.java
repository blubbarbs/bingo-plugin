package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftRedstoneLamp extends Goal {
    
    @Override
    public String getTitle() {
        return "Nightlight";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("redstone_lamp");
    }

    @Override
    public String getDescription() {
        return "Craft a Redstone Lamp.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.REDSTONE_LAMP
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        Player p = (Player) event.getWhoClicked();
        setCompletedFor(p);
    }
}
