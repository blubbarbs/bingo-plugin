package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;

public interface GoalData {    
    public Game getGame();
    public NBTCompound getSavedData();
        
    default String getName() {
        return getSavedData().getString("name");
    }

    default Team getWhoCompleted() {
        String teamName = getSavedData().getString("completed_by");

        return getGame().getTeam(teamName);
    }

    default boolean isCompleted() {
        return getWhoCompleted() != null;
    }

    default NBTCompound getTeamData(Team t) {
        return getSavedData().getOrCreateCompound("team_data").getOrCreateCompound(t.getName());
    }

    default boolean hasTeamCompleted(Team t) {
        return t.equals(getWhoCompleted());
    }
    
    default void setTeamCompleted(Team t, boolean completed) {        
        if (completed) {
            getSavedData().setString("completed_by", t.getName());
        }
        else {
            getSavedData().removeKey("completed_by");
        }
    }
}
