package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scoreboard.Team;

public interface StructureExploreData extends GoalData {
    default List<Structure> getTeamVisitedStructures(Team t) {
        ArrayList<Structure> visited = new ArrayList<Structure>();

        for (String s: getTeamData(t).getStringList("visited_structures")) {
            visited.add(Registry.STRUCTURE.get(NamespacedKey.fromString(s)));
        }

        return visited;
    }

    default boolean hasTeamVisitedStructure(Team t, Structure village) {
        return getTeamData(t).getStringList("visited_structures").contains(village.getKey().toString());
    }

    default void setTeamVisitedStructure(Team t, Structure village) {
        getTeamData(t).getStringList("visited_structures").add(village.getKey().toString());
    }
}
