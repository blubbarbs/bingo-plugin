package com.gmail.blubberalls.bingo.goal;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;

public abstract class EntityGoal extends NumerableGoal {        
    public EntityGoal(Game game, NBTCompound data) {
        super(game, data);
    }

    public NamespacedKey getEntityKey() {
        return NamespacedKey.fromString(data.getString("entity_target"));
    }

    public String getEntityName() {
        String[] capitalized = TextUtils.capitalizeFirstLetters(getEntityKey().getKey().split("_"));

        return TextUtils.join(capitalized, " ");
    }

    public String getIcon() {
        return "bingo.entity_icons." + getEntityName();
    }

    public boolean isTargetedEntity(Entity e) {
        return e.getType().getKey().equals(getEntityKey());
    }

    public void setEntityTargetKey(EntityType type) {
        data.setString("entity_target", type.getKey().toString());
    }
}
