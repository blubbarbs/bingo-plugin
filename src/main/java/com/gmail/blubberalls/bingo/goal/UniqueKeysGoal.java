package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.goal_data.KeyedData;

import net.md_5.bungee.api.ChatColor;

public abstract class UniqueKeysGoal extends ScoredGoal implements KeyedData {
    public abstract Keyed[] getValidKeys();
    
    @Override
    public int getGoal() {
        return getValidKeys().length;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {        
        String description = "Completed: " + ChatColor.AQUA + getCompletionFor(t) + "/" + getGoal();

        for (Keyed key : getValidKeys()) {
            description += ChatColor.RESET + "\n> ";
            description += containsUniqueKeyFor(t, key) ? ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + key.getKey().getKey() : ChatColor.DARK_AQUA + key.getKey().getKey();
        }

        return description.trim();
    }

    public boolean containsUniqueKeyFor(Team t, Keyed key) {
        return containsKeyedFor(t, "unique_keys", key);
    }

    public boolean containsUniqueKeyFor(Player p, Keyed key) {
        return containsUniqueKeyFor(game.getTeam(p), key);
    }

    public boolean isValidKey(Keyed key) {
        if (key == null) return false;

        for (Keyed k : getValidKeys()) {
            if (key.getKey().equals(k.getKey())) return true;
        }

        return false;
    }

    public void addUniqueKeyFor(Team t, Keyed key) {
        if (!isValidKey(key)
        ||  containsUniqueKeyFor(t, key)) return;

        addKeyedFor(t, "unique_keys", key);
        addCompletionFor(t, 1);
    }

    public void addUniqueKeyFor(Player p, Keyed key) {
        addUniqueKeyFor(game.getTeam(p), key);
    }
}
