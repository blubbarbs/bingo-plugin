package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class NumerableGoal extends Goal {
    public int getMinimumGoal() {
        return 1;
    }

    public int getMaximumGoal() {
        return 1;
    }

    @Override
    public String getTeamCompletionStatus(Team t) {
        return ChatColor.YELLOW + getTitle() + " " + ChatColor.GREEN + getTeamCompletion(t) + "/" + getGoal();         
    }

    @Override
    public void initializeNewGoal() {
        getSavedData().setInteger("goal", getGame().getRandom().nextInt(getMinimumGoal(), getMaximumGoal() + 1));
    }
}
