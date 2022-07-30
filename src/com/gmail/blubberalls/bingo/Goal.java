package com.gmail.blubberalls.bingo;

import org.bukkit.entity.Player;

import dev.jorel.commandapi.nbtapi.NBTCompound;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class Goal {
    private String name;
    
    public Goal(String name) {
        this.name = name;
    }

    public Goal(String name, NBTCompound compound) {
        this(name);
    }

    public String getName() {
        return name;
    }

    public TextComponent getIconTooltip() {
        
    }

    public abstract String getTitle();
    public abstract String getDescription();
    public abstract void createPlayerScoreboard(Player p);
    public abstract boolean isCompleted(Player p);
    public abstract NBTCompound toNBT();
}
