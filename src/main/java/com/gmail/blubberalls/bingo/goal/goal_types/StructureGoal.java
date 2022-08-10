package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.util.TextUtils;

public interface StructureGoal extends DefaultGoal {

    default NamespacedKey getStructureKey() {
        Bukkit.getLogger().info("" + getData().toString());

        return NamespacedKey.fromString(getData().getString("structure"));
    }

    default String getStructureName() {
        return TextUtils.capitalizeFirstLetters(getStructureKey().getKey(), "_", " ");
    }

    default Structure getStructure() {
        return Registry.STRUCTURE.get(getStructureKey());
    }

}
