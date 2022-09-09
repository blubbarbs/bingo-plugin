package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.goal_data.KeyedData;

import net.md_5.bungee.api.ChatColor;

public class UniqueKeysGoal extends NumerableGoal implements KeyedData {
    @Override
    public String getProgressDescriptionFor(Team t) {
        if (getScoreFor(t, "completion") == 0) return "";        
        
        String description = "Unique Keys" + ChatColor.RESET;
        
        for (NamespacedKey key : getKeysFor(t, "unique_keys")) {
            description += "\n> " + ChatColor.AQUA + key.getKey() + ChatColor.RESET;
        }

        return description;
    }

    public boolean containsUniqueKeyFor(Team t, Keyed key) {
        return containsKeyedFor(t, "unique_keys", key);
    }

    public boolean containsUniqueKeyFor(Player p, Keyed key) {
        return containsUniqueKeyFor(game.getTeam(p), key);
    }

    public void addUniqueKeyFor(Team t, Keyed key) {
        if (containsKeyedFor(t, "unique_keys", key)) return;

        addKeyedFor(t, "unique_keys", key);
        addCompletionFor(t, 1);
    }

    public void addUniqueKeyFor(Player p, Keyed key) {
        addUniqueKeyFor(game.getTeam(p), key);
    }
}
