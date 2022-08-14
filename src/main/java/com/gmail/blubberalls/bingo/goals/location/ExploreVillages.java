package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.LocationGoal;
import com.gmail.blubberalls.bingo.goal.goal_data.StructureExploreData;
import com.gmail.blubberalls.bingo.util.Checks;

public class ExploreVillages extends LocationGoal implements StructureExploreData {

    public static boolean isVillage(Structure s) {
        return s != null && s.getKey().getKey().startsWith("village");
    }

    @Override
    public int getGoal() {
        return 3;
    }

    @Override
    public void onPlayerLocation(Player p) {
        Structure at = Checks.getStructureAtLoc(p.getLocation());

        if (!isVillage(at)
            || hasTeamVisitedStructure(p, at)) return;
        
        Bukkit.getLogger().info("Added " + at.getKey().toString() + " to visited villages list");
        setTeamVisitedStructure(p, at);
        addTeamCompletion(p, 1);
    }
    
}
