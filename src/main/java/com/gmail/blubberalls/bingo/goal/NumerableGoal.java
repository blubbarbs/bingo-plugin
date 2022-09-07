package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;

import net.md_5.bungee.api.ChatColor;

public abstract class NumerableGoal extends Goal implements ScoreData {

    public int getGoal() {
        return 1;
    }

    public int getCompletionFor(Team t) {
        return getScoreFor(t, "completion");
    }

    public int getCompletionFor(Player p) {
        return getCompletionFor(game.getTeam(p));
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        return "Completed: " + ChatColor.AQUA + getCompletionFor(t) + "/" + getGoal();
    }

    @Override
    public String getSidebarTitleFor(Team t) {
        Team completor = getWhoCompleted();
        String titlePrefix = completor == null ? difficulty.getColor() + "" : ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH;
        String sidebar = titlePrefix + ChatColor.BOLD + getTitle();

        if (completor != null) {
            sidebar += ChatColor.RESET + "\n" + "> Completed by: " + completor.getColor() + completor.getDisplayName();
        }
        else {
            sidebar += ChatColor.RESET + "\n" + "> Completed: " + ChatColor.AQUA + getCompletionFor(t) + "/" + getGoal();
        }
        
        return sidebar;
    }

    public void setCompletionFor(Team t, int completion) {
        setScoreFor(t, "completion", completion);

        super.setCompletedFor(t, completion >= getGoal());
        game.getTeamPlayers(t).forEach(game::updatePlayerSidebar);
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
    public void setCompletedFor(Team t, boolean completed) {
        if (completed) {
            setCompletionFor(t, getGoal());
        }
        else {
            setCompletionFor(t, 0);
        }
    }
}
