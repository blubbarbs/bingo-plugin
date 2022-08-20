package com.gmail.blubberalls.bingo.goals.craft;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;

public class CraftDiamondSword extends Goal {

    @Override
    public String getIconPath() {
        return "bingo.icons.minecraft.golden_sword";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!game.isPlaying((Player) event.getWhoClicked())
            || event.getCurrentItem().getType() != Material.DIAMOND_SWORD) return;

        Team t = game.getTeam((Player) event.getWhoClicked());

        setCompletedBy(t);
    }
}
