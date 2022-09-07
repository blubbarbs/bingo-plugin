package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Leaderboard;
import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;

import net.md_5.bungee.api.ChatColor;

public abstract class ScoredCapturableGoal extends CapturableGoal implements ScoreData {
    
    public boolean teamCheck(Team t) {
        int highestScore = getHighestScore("completion");
        int teamScore = getCompletionFor(t);
        
        Bukkit.getLogger().info("AAAA " + highestScore + " " + teamScore);

        return highestScore > 0 && teamScore >= highestScore;
    }

    @Override
    public String getSidebarTitleFor(Team t) {        
        Team completor = getWhoCompleted();
        int teamScore = getCompletionFor(t);
        int topScore = getHighestScore("completion");
        String titlePrefix = completor == null ? difficulty.getColor() + "" : difficulty.getColor() + "" + ChatColor.STRIKETHROUGH;
        String sidebar = titlePrefix + ChatColor.BOLD + getTitle();
        
        sidebar += "\n> Your Score: " + ChatColor.AQUA + teamScore;

        if (completor != null && !t.equals(completor)) {
            sidebar += "\n> " + completor.getColor() +  completor.getDisplayName() + ChatColor.RESET + "'s Score: " + ChatColor.AQUA + topScore;
        }

        return sidebar;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        String desc = "Scores\n";
        Leaderboard<Team, Integer> leaderboard = getLeaderboard("completion");

        for (Team team: leaderboard.keySet()) { 
            desc += team.getColor() + team.getDisplayName() + ": " + ChatColor.AQUA + leaderboard.get(team) + ChatColor.RESET;
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
}
