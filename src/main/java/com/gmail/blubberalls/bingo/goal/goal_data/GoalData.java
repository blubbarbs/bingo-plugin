package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;

public interface GoalData {    
    public Game getGame();
    public NBTCompound getSavedData();
        
    default String getName() {
        return getSavedData().getString("name");
    }

    default String getWhoCompletedName() {
        return getSavedData().getString("completed_by");
    }

    default Team getWhoCompleted() {
        return getGame().getTeam(getWhoCompletedName());
    }

    default boolean isCompleted() {
        return !getSavedData().getString("completed_by").isEmpty();
    }

    default NBTCompound getDataFor(Team t) {
        return getSavedData().getOrCreateCompound("team_data").getOrCreateCompound(t.getName());
    }
    
    default void setCompletedFor(Team t, boolean completed) {                                
        if (completed) {
            getSavedData().setString("completed_by", t.getName());
        }
        else if (!completed && getWhoCompletedName().equals(t.getName())) {
            getSavedData().removeKey("completed_by");
        }
    }

    default void setCompletedFor(Player p, boolean completed) {
        setCompletedFor(getGame().getTeam(p), completed);
    }

    default void setCompletedFor(Team t) {
        setCompletedFor(t, true);
    }

    default void setCompletedFor(Player p) {
        setCompletedFor(p, true);
    }
}
