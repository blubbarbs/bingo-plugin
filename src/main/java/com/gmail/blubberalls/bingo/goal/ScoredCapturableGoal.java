package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Leaderboard;
import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;

import net.md_5.bungee.api.ChatColor;

public abstract class ScoredCapturableGoal extends CapturableGoal implements ScoreData {
    private Leaderboard<Team, Integer> scores;

    public boolean teamCheck(Team t) {
        Team highestTeam = scores.getKeyAt(0);
        
        if (highestTeam == null) return false;

        int highestScore = scores.get(highestTeam);
        int teamScore = getCompletionFor(t);
        
        return teamScore >= highestScore;
    }

    @Override
    public String getSidebarTitleFor(Team t) {        
        Team completor = getWhoCompleted();
        int teamScore = getCompletionFor(t);
        Team highestTeam = scores.getKeyAt(0);
        int topScore = highestTeam != null ? scores.get(highestTeam) : 0;
        String sidebar = difficulty.getColor() + "" + ChatColor.BOLD + getTitle();
        
        sidebar += "\n> " + t.getColor() + "Your " + ChatColor.RESET + "Score: " + ChatColor.AQUA + teamScore;

        if (completor != null && !t.equals(completor)) {
            sidebar += "\n> " + completor.getColor() +  completor.getDisplayName() + ChatColor.RESET + "'s Score: " + ChatColor.AQUA + topScore;
        }

        return sidebar;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        Leaderboard<Team, Integer> leaderboard = getLeaderboard("completion");

        if (leaderboard.size() == 0) return "";

        String desc = "Leaderboard\n";

        for (Team team: leaderboard.keySet()) { 
            desc += "> " + team.getColor() + team.getDisplayName() + ": " + ChatColor.AQUA + leaderboard.get(team) + ChatColor.RESET;
            desc += "\n";
        }

        return desc.trim();
    }

    public int getCompletionFor(Team t) {
        return getScoreFor(t, "completion");
    }

    public int getCompletionFor(Player p) {
        return getCompletionFor(game.getTeam(p));
    }

    public void setCompletionFor(Team t, int completion) {
        setScoreFor(t, "completion", completion);
        scores.put(t, completion);

        super.setCompletedFor(t, willTeamComplete(t));
        game.update();
    }

    public void setCompletionFor(Player p, int completion) {
        setCompletionFor(game.getTeam(p), completion);
    }

    public void addCompletionFor(Team t, int delta) {
        setCompletionFor(t, getScoreFor(t, "completion") + delta);
    }

    public void addCompletionFor(Player p, int delta) {
        addCompletionFor(game.getTeam(p), delta);
    }

    @Override
    public void load() {
        super.load();

        scores = getLeaderboard("completion");
    }

}
