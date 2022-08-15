package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

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

    default Team getWhoCompleted() {
        String teamName = getSavedData().getString("completed_by");

        return !teamName.isEmpty() ? Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName) : null;
    }

    default boolean isCompleted() {
        return getWhoCompleted() != null;
    }

    default NBTCompound getTeamData(Team t) {
        return getSavedData().getOrCreateCompound("team_data").getOrCreateCompound(t.getName());
    }

    default int getTeamCompletion(Team t) {
        return getTeamData(t).getInteger("completion");
    }

    default boolean hasTeamCompleted(Team t) {
        return getTeamCompletion(t) >= getGoal();
    }
    
    default boolean isPlayerSubscribed(Player p) {
        return !isCompleted();
        //return getSavedData().getUUIDList("subscribers").contains(p.getUniqueId());
    }

    default void setTeamCompletion(Team t, int completion) {
        getTeamData(t).setInteger("completion", completion);
        
        if (hasTeamCompleted(t)) {
            getSavedData().setString("completed_by", t.getName());
        }
    }

    default void addTeamCompletion(Team t, int delta) {
        int old = getTeamCompletion(t);
        
        setTeamCompletion(t, old + delta);
    }

    default void setTeamCompleted(Team t, boolean completed) {
        if (completed) {
            setTeamCompletion(t, getGoal());
        }
        else {
            setTeamCompletion(t, 0);
        }
    }

    default void setTeamCompleted(Team t) {
        setTeamCompletion(t, getGoal());
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
