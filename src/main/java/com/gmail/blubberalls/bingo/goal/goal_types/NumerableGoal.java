package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public interface NumerableGoal extends DefaultGoal {
    
    @Override
    default String getCompletionStatus(Player p) {
        return ChatColor.GRAY + getTitle() + " " + ChatColor.GREEN + getCompletion(p) + "/" + getGoalNumber();
    }
}
