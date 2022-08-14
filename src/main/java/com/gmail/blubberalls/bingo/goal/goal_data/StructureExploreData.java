package com.gmail.blubberalls.bingo.goal.goal_data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;

public interface StructureExploreData extends GoalData {
    default List<Structure> getTeamVisitedStructures(Player p) {
        ArrayList<Structure> visited = new ArrayList<Structure>();

        for (String s: getTeamData(p).getStringList("visited_structures")) {
            visited.add(Registry.STRUCTURE.get(NamespacedKey.fromString(s)));
        }

        return visited;
    }

    default boolean hasTeamVisitedStructure(Player p, Structure village) {
        return getTeamData(p).getStringList("visited_structures").contains(village.getKey().toString());
    }

    default void setTeamVisitedStructure(Player p, Structure village) {
        getTeamData(p).getStringList("visited_structures").add(village.getKey().toString());
    }
}
