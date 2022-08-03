package com.gmail.blubberalls.bingo.goal;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.Game;

import dev.jorel.commandapi.nbtapi.NBTCompound;

public abstract class EntityGoal extends Goal {        
    public EntityGoal(Game game, NBTCompound data) {
        super(game, data);
    }

    public NamespacedKey getEntityTargetKey() {
        return NamespacedKey.fromString(data.getString("entity_target"));
    }

    public String getIcon() {
        return "bingo.entity_icons." + this.getEntityTargetKey().getKey();
    }

    public boolean isTargetedEntity(Entity e) {
        return e.getType().getKey().equals(this.getEntityTargetKey());
    }

    public void setEntityTargetKey(EntityType type) {
        data.setString("entity_target", type.getKey().toString());
    }
}
