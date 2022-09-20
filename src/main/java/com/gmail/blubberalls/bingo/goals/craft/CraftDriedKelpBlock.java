package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftDriedKelpBlock extends Goal {
    
    @Override
    public String getTitle() {
        return "Healthy Kelp Meal";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("dried_kelp_block");
    }

    @Override
    public String getDescription() {
        return "Craft a Dried Kelp Block.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.DRIED_KELP_BLOCK
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        Player p = (Player) event.getWhoClicked();
        setCompletedFor(p);
    }
}
