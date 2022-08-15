package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;

public interface GoalData {
    public NBTCompound getSavedData();
    
    default int getGoal() {
        return getSavedData().hasKey("goal") ? getSavedData().getInteger("goal") : 1;
    }
    
    default String getName() {
        return getSavedData().getString("name");
    }

    default String getWhoCompleted() {
        return getSavedData().getString("completed_by");
    }

    default boolean isCompleted() {
        return getWhoCompleted() != null;
    }

    default NBTCompound getTeamData(Player p) {
        return getSavedData().getOrCreateCompound("team_data").getOrCreateCompound(TextUtils.getTeamName(p));
    }

    default int getTeamCompletion(Player p) {
        return getTeamData(p).getInteger("completion");
    }

    default boolean hasTeamCompleted(Player p) {
        return getTeamCompletion(p) >= getGoal();
    }
    
    default boolean isPlayerSubscribed(Player p) {
        return !hasTeamCompleted(p) && true;
    }

    default void setTeamCompletion(Player p, int completion) {
        getTeamData(p).setInteger("completion", completion);
        
        if (hasTeamCompleted(p)) {
            setPlayerSubscription(p, false);
            getSavedData().setString("completed_by", TextUtils.getTeamName(p));
        }
    }

    default void addTeamCompletion(Player p, int delta) {
        int old = getTeamCompletion(p);
        
        setTeamCompletion(p, old + delta);
    }

    default void setTeamCompleted(Player p, boolean completed) {
        if (completed) {
            setTeamCompletion(p, getGoal());
        }
        else {
            setTeamCompletion(p, 0);
        }
    }

    default void setTeamCompleted(Player p) {
        setTeamCompletion(p, getGoal());
    }

    default void setPlayerSubscription(Player p, boolean subscribe) {
        NBTList<UUID> subscribers = getSavedData().getUUIDList("subscribers");

        if (subscribe && !subscribers.contains(p.getUniqueId())) {
            subscribers.add(p.getUniqueId());
        }
        else {
            subscribers.remove(p.getUniqueId());
        }
    }
}
