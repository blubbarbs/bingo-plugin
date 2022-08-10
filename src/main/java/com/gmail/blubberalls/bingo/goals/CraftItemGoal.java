package com.gmail.blubberalls.bingo.goals;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.goal_types.MaterialGoal;
import com.gmail.blubberalls.bingo.goal.goal_types.NumerableGoal;

import de.tr7zw.nbtapi.NBTCompound;

public class CraftItemGoal extends Goal implements MaterialGoal, NumerableGoal {

    public CraftItemGoal(Game game, NBTCompound data) {
        super(game, data);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Craft " + getMaterialName();
    }

    @Override
    public String getDescription() {
        return "Craft " + getGoalNumber() + " " + getMaterialName() + "(s)!";
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Bukkit.getLogger().info("???");

        Bukkit.getLogger().info("" + event.getCurrentItem() + " " + event.getCurrentItem());

        if (!isTargetedMaterial(event.getCurrentItem())
            || isCompleted((Player) event.getWhoClicked())) return;

        Player whoClicked = (Player) event.getWhoClicked();

        addCompletion(whoClicked, 1);

        Bukkit.getLogger().info("SUCCESS!");
    }
    
}
