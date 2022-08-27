package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.goal_data.CompletionData;

import net.md_5.bungee.api.ChatColor;

public abstract class NumerableGoal extends Goal implements CompletionData {

    public int getGoal() {
        return 1;
    }

    @Override
    public String getCompletionStatusFor(Team t) {
        if (isCompleted()) {
            if (t.equals(getWhoCompleted())) {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.GREEN + " ✓";
            }
            else {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.RED + " ✕";
            }
        }
        else {
            return ChatColor.YELLOW + getTitle() + " " + ChatColor.AQUA + getCompletionFor(t) + "/" + getGoal();         
        }
    }

    @Override
    public void setCompletionFor(Team t, int completion) {
        CompletionData.super.setCompletionFor(t, completion);

        super.setCompletedFor(t, completion >= getGoal());
        game.getPlayers().forEach(game::updatePlayerSidebar);
    }

    @Override
    public void setCompletedFor(Team t, boolean completed) {
        if (completed) {
            setCompletionFor(t, getGoal());
        }
        else {
            setCompletionFor(t, 0);
        }
    }
}
