package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class CraftMagmaBlock extends Goal {
    
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!game.isPlayerPlaying((Player) event.getWhoClicked())
        ||  event.getCurrentItem().getType() != Material.MAGMA_BLOCK) return;

        Player p = (Player) event.getWhoClicked();

        setCompletedFor(p);
    }
}