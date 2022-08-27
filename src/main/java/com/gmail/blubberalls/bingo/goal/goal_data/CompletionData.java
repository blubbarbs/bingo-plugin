package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface CompletionData extends GoalData {
    default int getCompletionFor(Team t) {
        return getDataFor(t).getInteger("completion");
    }

    default int getCompletionFor(Player p) {
        return getCompletionFor(getGame().getTeam(p));
    }

    default void setCompletionFor(Team t, int completion) {
        getDataFor(t).setInteger("completion", completion);
    }

    default void setCompletionFor(Player p, int completion) {
        setCompletionFor(getGame().getTeam(p), completion);
    }

    default void addCompletionFor(Team t, int delta) {
        int old = getCompletionFor(t);
        
        setCompletionFor(t, old + delta);
    }

    default void addCompletionFor(Player p, int delta) {
        addCompletionFor(getGame().getTeam(p), delta);
    }
}
