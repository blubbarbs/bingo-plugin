package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface UUIDData extends GoalData {
    default Collection<UUID> getUUIDsFor(Team t, String key) {
        ArrayList<UUID> uuids = new ArrayList<UUID>();

        for (String uuidString : getDataFor(t).getStringList(key)) {
            uuids.add(UUID.fromString(uuidString));
        }

        return uuids;
    }

    default Collection<UUID> getUUIDsFor(Player p, String key) {
        return getUUIDsFor(getGame().getTeam(p), key);
    }

    default UUID getUUIDFor(Team t, String key) {
        return UUID.fromString(getDataFor(t).getString(key));
    }

    default UUID getUUIDFor(Player p, String key) {
        return getUUIDFor(getGame().getTeam(p), key);
    }

    default void setUUIDFor(Team t, String key, UUID value) { 
        getDataFor(t).setString(key, value.toString());
    }

    default void setUUIDFor(Player p, String key, UUID value) {
        setUUIDFor(getGame().getTeam(p), key, value);
    }

    default void addUUIDFor(Team t, String key, UUID value) {
        getDataFor(t).getStringList(key).add(value.toString());
    }

    default void addUUIDFor(Player p, String key, UUID value) {
        addUUIDFor(getGame().getTeam(p), key, value);
    }
}
