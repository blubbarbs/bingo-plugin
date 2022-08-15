package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.NumerableGoal;
import com.gmail.blubberalls.bingo.goal.goal_data.StructureExploreData;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.PlayerExistEvent;

public class ExploreVillages extends NumerableGoal implements StructureExploreData {

    public static boolean isVillage(Structure s) {
        return s != null && s.getKey().getKey().startsWith("village");
    }

    @Override
    public int getGoal() {
        return 3;
    }

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());
        Team t = getTeam(event.getPlayer());

        if (!isVillage(at)
            || hasTeamVisitedStructure(t, at)) return;
        
        Bukkit.getLogger().info("Added " + at.getKey().toString() + " to visited villages list");
        setTeamVisitedStructure(t, at);
        addTeamCompletion(t, 1);
    }
    
}
