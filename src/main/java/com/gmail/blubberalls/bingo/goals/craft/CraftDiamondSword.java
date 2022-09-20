package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CraftDiamondSword extends Goal {

    @Override
    public String getTitle() {
        return "Craft Diamond Sword";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("diamond_sword");
    }

    @Override
    public String getDescription() {
        return "Craft a Diamond Sword.";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getCurrentItem().getType() != Material.DIAMOND_SWORD
        ||  !game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        Player p = (Player) event.getWhoClicked();
        setCompletedFor(p);
    }
}
