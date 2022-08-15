package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.goal_data.StructureExploreData;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.PlayerExistEvent;

public class ExploreVillages extends Goal implements StructureExploreData {

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

        if (!isVillage(at)
            || hasTeamVisitedStructure(event.getPlayer(), at)) return;
        
        Bukkit.getLogger().info("Added " + at.getKey().toString() + " to visited villages list");
        setTeamVisitedStructure(event.getPlayer(), at);
        addTeamCompletion(event.getPlayer(), 1);
    }
    
}
