package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public abstract class NumerableGoal extends Goal {
    public int getMinimumGoal() {
        return 1;
    }

    public int getMaximumGoal() {
        return 1;
    }

    @Override
    public String getTeamCompletionStatus(Team t) {
        if (isCompleted()) {
            if (t.equals(getWhoCompleted())) {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.GREEN + " ✓";
            }
            else {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.RED + " ✕";
            }
        }
        else {
            return ChatColor.YELLOW + getTitle() + " " + ChatColor.AQUA + getTeamCompletion(t) + "/" + getGoal();         
        }
    }

    @Override
    public void initializeNewGoal() {
        getSavedData().setInteger("goal", getGame().getRandom().nextInt(getMinimumGoal(), getMaximumGoal() + 1));
    }
}
