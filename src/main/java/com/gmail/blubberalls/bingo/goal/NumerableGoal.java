package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.goal_data.CompletionData;

import net.md_5.bungee.api.ChatColor;

public abstract class NumerableGoal extends Goal implements CompletionData {

    public int getGoal() {
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
    public void setTeamCompletion(Team t, int completion) {
        Team oldCompletor = getWhoCompleted();

        CompletionData.super.setTeamCompletion(t, completion);

        if (oldCompletor == null && completion >= getGoal()) {
            super.setCompletedBy(t);
        }
        else if (t.equals(oldCompletor) && completion < getGoal()) {
            super.setCompletedBy(null);
        }
        else {
            game.update();
        }
    }

    @Override
    public void setCompletedBy(Team t) {
        if (t == null) {
            setTeamCompletion(getWhoCompleted(), 0);
        }
        else {
            setTeamCompletion(t, getGoal());
        }
    }
}
