package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.scoreboard.Team;

public interface CompletionData extends GoalData {
    default int getTeamCompletion(Team t) {
        return getTeamData(t).getInteger("completion");
    }

    default void setTeamCompletion(Team t, int completion) {
        getTeamData(t).setInteger("completion", completion);
    }

    default void addTeamCompletion(Team t, int delta) {
        int old = getTeamCompletion(t);
        
        setTeamCompletion(t, old + delta);
    }
}
