package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CapturableGoal extends Goal {
    @Override
    public String getTeamCompletionStatus(Player p) {
        return ChatColor.GOLD + getTitle();         
    }

    @Override
    public void onCompletionUpdate() {
        game.update();
    }
}
