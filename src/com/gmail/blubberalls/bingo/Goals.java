package com.gmail.blubberalls.bingo;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal_factories.EntityGoalFactory;
import com.gmail.blubberalls.bingo.goal_factories.GoalFactory;
import com.gmail.blubberalls.bingo.goals.KillEntityGoal;

import dev.jorel.commandapi.nbtapi.NBTCompound;

public enum Goals {

    KILL_CREEPERS(new EntityGoalFactory<KillEntityGoal>("kill_creepers", KillEntityGoal.class)
                    .setEntityType(EntityType.CREEPER)
                    .setMaximumGoal(10));

    private GoalFactory<?> factory;

    private Goals(GoalFactory<?> factory) {
        this.factory = factory;

        Goals.registerGoalFactory(factory);
    }
    
    public String getName() {
        return this.factory.getName();
    }

    public GoalFactory<?> getFactory() {
        return this.factory;
    }

    private static final HashMap<String, GoalFactory<?>> goalFactories = new HashMap<String, GoalFactory<?>>();

    private static void registerGoalFactory(GoalFactory<?> factory) {
        goalFactories.put(factory.getName(), factory);
    }

    public static Goal createGoal(Game game, String key) {
        GoalFactory<?> factory = goalFactories.get(key);
        
        return factory.createGoal(game);
    }

    public static Goal loadGoal(Game game, NBTCompound data) {
        GoalFactory<?> factory = goalFactories.get(data.getString("name"));
        
        return factory.loadGoal(game, data);
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        return null;
    }
}
