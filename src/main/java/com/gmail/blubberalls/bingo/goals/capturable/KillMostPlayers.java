package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Leaderboard;
import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;

import net.md_5.bungee.api.ChatColor;

public class KillMostPlayers extends CapturableGoal implements ScoreData {
    
    public int getTopKills() {
        Leaderboard<Team, Integer> leaderboard = getLeaderboard("killed");

        return leaderboard.size() > 0 ? leaderboard.values().get(0) : 0;
    }

    public boolean teamHasMostKills(Team team) {
        Leaderboard<Team, Integer> leaderboard = getLeaderboard("killed");
        
        if (leaderboard.size() == 0) return false;
        
        int topScore = leaderboard.values().get(0);

        return topScore > 0 && getScoreFor(team, "killed") == topScore;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        int teamKills = getScoreFor(t, "killed");
        String message = "Top Kills: " + ChatColor.AQUA + getTopKills() + ChatColor.RESET  + "\n";
        message += "Your Kills: " + ChatColor.AQUA + teamKills + ChatColor.RESET + "";

        return message;
    }
    
    @Override
    public String getSidebarTitleFor(Team t) {
        String basic = ChatColor.LIGHT_PURPLE + "Kill Players " + ChatColor.AQUA + "(" + getScoreFor(t, "killed") + ")";
        Team completor = getWhoCompleted();

        return isCompleted() ? basic + ChatColor.RESET + " : " + ChatColor.ITALIC + completor.getColor() + completor.getDisplayName() + completor.getColor() + " (" + getScoreFor(completor, "killed") + ")" : basic;
    }

    // @EventHandler
    // public void onPlayerDeath(PlayerDeathEvent event) {
    //     if (!game.isPlayerPlaying(event.getEntity())
    //     ||  event.getEntity().getKiller() != null
    //     ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
    //     Player p = event.getEntity();
    //     Player killer = p.getKiller();

    //     if (game.getTeam(p).equals(game.getTeam(killer))) return;

    //     addScoreFor(p, "killed", 1);
    //     setCompletedFor(p, teamHasMostKills(game.getTeam(p)));
    // }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player killer = (Player) event.getEntity().getKiller();

        if (!game.isPlayerPlaying(killer)) return;

        addScoreFor(killer, "killed", 1);
        setCompletedFor(killer, teamHasMostKills(game.getTeam(killer)));
        game.getTeamPlayers(game.getTeam(killer)).forEach(game::updatePlayerSidebar);
    }
}
