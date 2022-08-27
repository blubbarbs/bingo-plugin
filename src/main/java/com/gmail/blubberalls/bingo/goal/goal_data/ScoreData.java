package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public interface ScoreData extends GoalData {
    default int getScoreFor(Team t, String key) {
        return getDataFor(t).getInteger(key) != null ? getDataFor(t).getInteger(key) : 0;
    }

    default int getScoreFor(Player p, String key) {
        return getScoreFor(getGame().getTeam(p), key);
    }

    default void setScoreFor(Team t, String key, int completion) {
        getDataFor(t).setInteger(key, completion);
    }

    default void setScoreFor(Player p, String key, int completion) {
        setScoreFor(getGame().getTeam(p), key, completion);
    }

    default void addScoreFor(Team t, String key, int delta) {
        int old = getScoreFor(t, key);
        
        setScoreFor(t, key, old + delta);
    }

    default void addScoreFor(Player p, String key, int delta) {
        addScoreFor(getGame().getTeam(p), key, delta);
    }
}
