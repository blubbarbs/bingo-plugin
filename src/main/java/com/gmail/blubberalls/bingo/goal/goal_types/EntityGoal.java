package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.util.TextUtils;

public interface EntityGoal extends DefaultGoal {        
    default NamespacedKey getEntityKey() {
        return NamespacedKey.fromString(getData().getString("entity"));
    }

    default String getEntityName() {
        return TextUtils.capitalizeFirstLetters(getEntityKey().getKey(), "_", " ");
    }

    default EntityType getEntityType() {
        return Registry.ENTITY_TYPE.get(getEntityKey());
    }

    default boolean isTargetedEntity(Entity e) {
        return e.getType() == getEntityType();
    }
}
