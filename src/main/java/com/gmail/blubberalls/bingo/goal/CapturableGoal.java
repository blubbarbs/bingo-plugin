package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class CapturableGoal extends Goal {
    
    @Override
    public boolean shouldRegisterEvents() {
        return true;
    }

    @Override
    public boolean shouldUnregisterEvents() {
        return false;
    }

    @Override
    public String getTeamCompletionStatus(Team t) {
        return ChatColor.GOLD + getTitle();         
    }
}
