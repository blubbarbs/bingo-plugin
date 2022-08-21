package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.util.RegistryDataAccessor;

public interface KeyedData extends GoalData {
    default Collection<NamespacedKey> getKeys(Team t, String key) {
        ArrayList<NamespacedKey> keys = new ArrayList<>();
        
        for (String keyString : getTeamData(t).getStringList(key)) {
            keys.add(NamespacedKey.fromString(keyString));
        }

        return keys;
    }

    default <T extends Keyed> Collection<T> getKeyeds(Team t, String key, Registry<T> registry) {
        return RegistryDataAccessor.convertKeys(getKeys(t, key), registry);
    }

    default Collection<Structure> getStructures(Team t, String key) {
        return getKeyeds(t, key, Registry.STRUCTURE);
    }

    default Collection<Material> getMaterials(Team t, String key) {
        return getKeyeds(t, key, Registry.MATERIAL);
    }

    default NamespacedKey getKey(Team t, String key) {
        return NamespacedKey.fromString(getTeamData(t).getString(key));
    }

    default <T extends Keyed> T getKeyed(Team t, String key, Registry<T> registry) {
        return RegistryDataAccessor.convertKey(getKey(t, key), registry);
    }

    default Structure getStructure(Team t, String key) {
        return getKeyed(t, key, Registry.STRUCTURE);
    }

    default Material getMaterial(Team t, String key) {
        return getKeyed(t, key, Registry.MATERIAL);
    }
    
    default boolean containsKey(Team t, String key, String value) {
        return getTeamData(t).getStringList(key).contains(value);
    }

    default boolean containsKey(Team t, String key, NamespacedKey value) {
        return containsKey(t, key, value.toString());
    }

    default boolean containsKeyed(Team t, String key, Keyed value) {
        return containsKey(t, key, value.getKey());
    }

    default void addKey(Team t, String key, String value) {
        getTeamData(t).getStringList(key).add(value);
    }

    default void addKey(Team t, String key, NamespacedKey value) {
        addKey(t, key, value.toString());
    }

    default void addKeyed(Team t, String key, Keyed value) {
        addKey(t, key, value.getKey());
    }

    default void setKey(Team t, String key, String value) {
        getTeamData(t).setString(key, value);
    }

    default void setKey(Team t, String key, NamespacedKey value) {
        setKey(t, key, value.toString());
    }

    default void setKeyed(Team t, String key, Keyed value) {
        setKey(t, key, value.getKey());
    }
}
