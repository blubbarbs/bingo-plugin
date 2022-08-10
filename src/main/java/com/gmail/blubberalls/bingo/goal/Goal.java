package com.gmail.blubberalls.bingo.goal;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_types.DefaultGoal;

import de.tr7zw.nbtapi.NBTCompound;

public abstract class Goal implements DefaultGoal {
    protected Game game;
    protected NBTCompound data;

    public Goal(Game game, NBTCompound data) {
        this.game = game;
        this.data = data;
    }
    
    public abstract String getIcon();
    public abstract String getTitle();
    public abstract String getDescription();

    public Game getGame() {
        return game;
    }

    public NBTCompound getData() {
        return data;
    }
}
