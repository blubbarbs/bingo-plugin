package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftPumpkinPie extends Goal {
    
    @Override
    public String getTitle() {
        return "The Great Pumpkin (Pie)";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("pumpkin_pie");
    }

    @Override
    public String getDescription() {
        return "Craft a Pumpkin Pie.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.PUMPKIN_PIE
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        Player p = (Player) event.getWhoClicked();
        setCompletedFor(p);   
    }
}
