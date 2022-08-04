package com.gmail.blubberalls.bingo.goal_factories;

import java.lang.reflect.Constructor;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.Goal;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;

public class GoalFactory<T extends Goal> {
    private String name;
    private Class<T> productClass;

    public GoalFactory(String name, Class<T> productClass) {
        this.name = name;
        this.productClass = productClass;
    }

    public String getName() {
        return name;
    }

    protected T newGoal(Game game) {
        T goal = loadGoal(game, new NBTContainer());

        goal.getData().setString("name", this.name);

        return goal;
    }

    public T loadGoal(Game game, NBTCompound data) {
        try {
            Constructor<T> constructor = productClass.getConstructor(Game.class, NBTCompound.class);
            T goal = constructor.newInstance(game, data);

            return goal;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T createGoal(Game game) {
        return newGoal(game);
    }
}