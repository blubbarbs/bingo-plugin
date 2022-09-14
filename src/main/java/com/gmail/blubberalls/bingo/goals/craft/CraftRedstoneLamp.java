package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class CraftRedstoneLamp extends Goal {
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.REDSTONE_LAMP
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        Player p = (Player) event.getWhoClicked();
        setCompletedFor(p);
    }
}
