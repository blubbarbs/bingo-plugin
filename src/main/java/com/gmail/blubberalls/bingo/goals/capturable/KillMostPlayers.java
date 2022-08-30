package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;

public class KillMostPlayers extends CapturableGoal implements ScoreData {
    public boolean teamHasMostKills(Team team) {
        
        int teamScore = getScoreFor(team, "killed");

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!game.isPlayerPlaying(event.getEntity())
        ||  event.getEntity().getKiller() != null
        ||  !game.isPlayerPlaying(event.getEntity().getKiller())) return;
    
        Player p = event.getEntity();
        Player killer = p.getKiller();

        if (game.getTeam(p).equals(game.getTeam(killer))) return;

        addScoreFor(p, "killed", 1);
        setCompletedFor(p, teamHasMostKills(game.getTeam(p)));
    }
}
