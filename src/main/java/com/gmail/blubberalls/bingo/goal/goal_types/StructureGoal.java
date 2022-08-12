package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.util.TextUtils;

public interface StructureGoal extends DefaultGoal {
    public static String KEY = "structure";

    default NamespacedKey getStructureKey() {
        return NamespacedKey.fromString(getGoalData().getString("structure"));
    }

    default String getStructureName() {
        return TextUtils.capitalizeFirstLetters(getStructureKey().getKey(), "_", " ");
    }

    default Structure getStructure() {
        return Registry.STRUCTURE.get(getStructureKey());
    }

}
