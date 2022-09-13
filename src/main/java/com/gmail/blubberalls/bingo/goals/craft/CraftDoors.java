package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class CraftDoors extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Door Hoarder";
    }

    @Override
    public String getDescription() {
        return "Craft all door types.";
    }

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.OAK_DOOR,
            Material.SPRUCE_DOOR,
            Material.BIRCH_DOOR,
            Material.JUNGLE_DOOR,
            Material.ACACIA_DOOR,
            Material.DARK_OAK_DOOR,
            Material.IRON_DOOR
        };
    }
    
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!game.isPlayerPlaying((Player) event.getWhoClicked())) return;

        addUniqueKeyFor((Player) event.getWhoClicked(), event.getCurrentItem().getType());
    }

}
