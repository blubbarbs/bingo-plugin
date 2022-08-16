package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public abstract class CapturableGoal extends Goal {

    public abstract boolean testCondition(Player p);

    public boolean shouldComplete(Team t) {
        return (!isCompleted() || t.equals(getWhoCompleted())) && game.getTeamPlayers(t).stream().allMatch(this::testCondition);
    }

    @Override
    public boolean hasEventsWhenCompleted() {
        return true;
    }

    @Override
    public String getTeamCompletionStatus(Team t) {
        if (isCompleted()) {
            Team completor = getWhoCompleted();

            if (t.equals(getWhoCompleted())) {
                return ChatColor.LIGHT_PURPLE + "" + getTitle() + ChatColor.RESET + " : " + completor.getColor() + ChatColor.ITALIC + "YOU";
            }
            else {
                return ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + " : " + completor.getColor() + ChatColor.ITALIC + completor.getDisplayName();
            }
        }
        else {
            return ChatColor.LIGHT_PURPLE + getTitle();         
        }
    }
}
