package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface KeyedData extends GoalData {
    default Collection<NamespacedKey> getKeysFor(Team t, String key) {
        ArrayList<NamespacedKey> keys = new ArrayList<>();
        
        for (String keyString : getDataFor(t).getStringList(key)) {
            keys.add(NamespacedKey.fromString(keyString));
        }

        return keys;
    }

    default Collection<NamespacedKey> getKeysFor(Player p, String key) {
        return getKeysFor(getGame().getTeam(p), key);
    }

    default NamespacedKey getKeyFor(Team t, String key) {
        return NamespacedKey.fromString(getDataFor(t).getString(key));
    }
    
    default NamespacedKey getKeyFor(Player p, String key) {
        return getKeyFor(getGame().getTeam(p), key);
    }

    default boolean containsKeyedFor(Team t, String key, Keyed value) {
        return getDataFor(t).getStringList(key).contains(value.getKey().toString());
    }

    default boolean containsKeyedFor(Player p, String key, Keyed value) {
        return containsKeyedFor(getGame().getTeam(p), key, value);
    }

    default void addKeyedFor(Team t, String key, Keyed value) {
        Collection<NamespacedKey> oldValues = getKeysFor(t, key);
        
        if (oldValues.contains(value.getKey())) return;

        getDataFor(t).getStringList(key).add(value.getKey().toString());    
        onKeyedListDataChange(t, key, oldValues);
    }

    default void addKeyedFor(Player p, String key, Keyed value) {
        addKeyedFor(getGame().getTeam(p), key, value);  
    }

    default void setKeyed(Team t, String key, Keyed value) {
        NamespacedKey oldValue = getKeyFor(t, key);
        
        if (value.getKey().equals(oldValue)) return;

        getDataFor(t).setString(key, value.getKey().toString());
        onKeyedDataChange(t, key, oldValue);
    }

    default void setKeyed(Player p, String key, Keyed value) {
        setKeyed(getGame().getTeam(p), key, value);
    }

    default void onKeyedDataChange(Team t, String key, NamespacedKey oldValue) {}
    default void onKeyedListDataChange(Team t, String key, Collection<NamespacedKey> oldValue) {}
}
