package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;

public interface GoalData {
    public Game getGame();
    public NBTCompound getSavedData();
    
    default int getGoal() {
        return getSavedData().hasKey("goal") ? getSavedData().getInteger("goal") : 1;
    }
    
    default String getName() {
        return getSavedData().getString("name");
    }

    default Team getWhoCompleted() {
        String teamName = getSavedData().getString("completed_by");

        return !teamName.isEmpty() ? getGame().getTeam(teamName) : null;
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
    
    default void setTeamCompletion(Team t, int completion) {
        Team oldCompletor = getWhoCompleted();

        if (oldCompletor != null && oldCompletor.equals(t) && completion < getGoal()) {
            getSavedData().removeKey("completed_by");
        }
        else if (completion >= getGoal()) {
            getSavedData().setString("completed_by", t.getName());
        }

        getTeamData(t).setInteger("completion", completion);
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
}
