package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.scoreboard.Team;

public interface KeyedData extends GoalData {
    default Collection<NamespacedKey> getKeys(Team t, String key) {
        ArrayList<NamespacedKey> keys = new ArrayList<>();
        
        for (String keyString : getTeamData(t).getStringList(key)) {
            keys.add(NamespacedKey.fromString(keyString));
        }

        return keys;
    }

    default NamespacedKey getKey(Team t, String key) {
        return NamespacedKey.fromString(getTeamData(t).getString(key));
    }
    
    default boolean containsKeyed(Team t, String key, Keyed value) {
        return getTeamData(t).getStringList(key).contains(value.getKey().toString());
    }

    default void addKeyed(Team t, String key, Keyed value) {
        getTeamData(t).getStringList(key).add(value.getKey().toString());    
    }

    default void setKeyed(Team t, String key, Keyed value) {
        getTeamData(t).setString(key, value.getKey().toString());
    }
}
