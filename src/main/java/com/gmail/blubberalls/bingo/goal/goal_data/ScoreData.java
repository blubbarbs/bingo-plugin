package com.gmail.blubberalls.bingo.goal.goal_data;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Leaderboard;

public interface ScoreData extends GoalData {
    default Leaderboard<Team, Integer> getLeaderboard(String key) {
        Leaderboard<Team, Integer> leaderboard = new Leaderboard<Team, Integer>();

        for (String teamName : getSavedData().getOrCreateCompound("team_data").getKeys()) {
            Team team = getGame().getTeam(teamName);
            int score = getScoreFor(team, key);

            leaderboard.put(team, score);
        }

        return leaderboard;
    }
    
    default int getHighestScore(String key) {
        return getLeaderboard(key).values().get(0);
    }

    default int getScoreFor(Team t, String key) {
        return getDataFor(t).getInteger(key) != null ? getDataFor(t).getInteger(key) : 0;
    }

    default int getScoreFor(Player p, String key) {
        return getScoreFor(getGame().getTeam(p), key);
    }

    default void setScoreFor(Team t, String key, int newScore) {        
        getDataFor(t).setInteger(key, newScore);
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
