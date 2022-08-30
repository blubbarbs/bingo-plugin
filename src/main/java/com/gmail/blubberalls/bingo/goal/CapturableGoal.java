package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public abstract class CapturableGoal extends Goal {
    
    @Override
    public String getCompletionDescriptionFor(Team t) {
        Team completor = getWhoCompleted();

        return completor != null ? ChatColor.ITALIC  + "Currently Captured By: " + completor.getColor() + completor.getDisplayName() : "";
    }

    @Override
    public String getCompletionStatusFor(Team t) {
        Team completor = getWhoCompleted();

        if (completor != null) {
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
