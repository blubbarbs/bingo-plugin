package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.NumerableGoal;
import com.gmail.blubberalls.bingo.goal.goal_data.KeyedData;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class ExploreVillages extends NumerableGoal implements KeyedData {

    public boolean isVillage(Structure s) {
        return s != null && s.getKey().getKey().startsWith("village");
    }

    @Override
    public int getGoal() {
        return 3;
    }

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        Player p = event.getPlayer();
        Team t = game.getTeam(p);
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());

        if (!game.isPlayerPlaying(p)
        ||  !isVillage(at)
        ||  containsKeyedFor(t, "visited_structures", at)) return;
        
        Bukkit.getLogger().info("Added " + at.getKey().toString() + " to visited villages list");
        addKeyedFor(t, "visited_structures", at);
        addCompletionFor(t, 1);
    }
    
}
