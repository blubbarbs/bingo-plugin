package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftMagmaBlock extends Goal {
    
    @Override
    public String getTitle() {
        return "Team Magma";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("magma_block");
    }

    @Override
    public String getDescription() {
        return "Craft a Magma Block.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.MAGMA_BLOCK) return;

        Player p = (Player) event.getWhoClicked();

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
