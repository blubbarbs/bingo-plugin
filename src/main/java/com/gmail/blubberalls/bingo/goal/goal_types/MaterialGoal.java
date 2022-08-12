package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.util.TextUtils;

public interface MaterialGoal extends DefaultGoal {
    public static String KEY = "material";

    default NamespacedKey getMaterialKey() {
        return NamespacedKey.fromString(getGoalData().getString("material"));
    }

    default String getMaterialName() {
        return TextUtils.capitalizeFirstLetters(getMaterialKey().getKey(), "_", " ");
    }

    default Material getMaterial() {
        return Registry.MATERIAL.get(getMaterialKey());
    }

    default boolean isTargetedMaterial(ItemStack stack) {
        return stack.getType() == getMaterial();
    }
}
