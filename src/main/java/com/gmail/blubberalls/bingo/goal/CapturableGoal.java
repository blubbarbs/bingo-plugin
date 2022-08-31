package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public abstract class CapturableGoal extends Goal {
    
    @Override
    public String getCompletionDescription() {
        Team completor = getWhoCompleted();

        return completor != null ? ChatColor.ITALIC  + "Current Holder: " + completor.getColor() + ChatColor.ITALIC + completor.getDisplayName() : "";
    }

    public String getTooltipFor(Team t) {
        String tooltip = difficulty.getColor() + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + getTitle() +  ChatColor.RESET;
        String description = getDescription();
        String progressDescription = getProgressDescriptionFor(t);
        String completionDescription = getCompletionDescription();
        
        if (!description.isEmpty()) {
            tooltip += "\n" + description;
        }

        if (!progressDescription.isEmpty()) {
            tooltip += "\n\n" + progressDescription;
        }
        
        if (!completionDescription.isEmpty()) {
            tooltip += "\n\n" + completionDescription;
        }

        return tooltip;
    }

    @Override
    public String getSidebarTitleFor(Team t) {
        Team completor = getWhoCompleted();

        if (completor != null) {
            if (t.equals(getWhoCompleted())) {
                return ChatColor.LIGHT_PURPLE + "" + getTitle() + ChatColor.RESET + " : " + t.getColor() + ChatColor.ITALIC + t.getDisplayName();
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
