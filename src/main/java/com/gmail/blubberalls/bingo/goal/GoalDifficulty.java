package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.util.TextComponents;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TranslatableComponent;

public enum GoalDifficulty {
    EASY("bingo.frames.easy", ChatColor.GREEN),
    MEDIUM("bingo.frames.medium", ChatColor.YELLOW),
    HARD("bingo.frames.hard", ChatColor.RED),
    CAPTURABLE("bingo.frames.capturable", ChatColor.LIGHT_PURPLE);

    private String frameIconDirectory;
    private ChatColor color;

    GoalDifficulty(String frameIconDirectory, ChatColor color) {
        this.frameIconDirectory = frameIconDirectory;
        this.color = color;
    }

    public TranslatableComponent getFrameFor(Team t) {
        return t != null ? TextComponents.icon(frameIconDirectory + ".frame_" + t.getName().toLowerCase()) : TextComponents.icon(frameIconDirectory + ".frame_none");
    }

    public ChatColor getColor() {
        return color;
    }
}
