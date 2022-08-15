package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class NumerableGoal extends Goal {
    public int getMinimumGoal() {
        return 1;
    }

    public int getMaximumGoal() {
        return 1;
    }

    @Override
    public String getTeamCompletionStatus(Player p) {
        return ChatColor.AQUA + getTitle() + " " + ChatColor.GREEN + getTeamCompletion(p) + "/" + getGoal();         
    }

    @Override
    public void initializeNewGoal() {
        getSavedData().setInteger("goal", getGame().getRandom().nextInt(getMinimumGoal(), getMaximumGoal() + 1));
    }
}
