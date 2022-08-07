package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal_factories.EntityGoalFactory;
import com.gmail.blubberalls.bingo.goal_factories.GoalFactory;
import com.gmail.blubberalls.bingo.goals.KillEntityGoal;

import de.tr7zw.nbtapi.NBTCompound;

public enum Goals {

    KILL_CREEPERS(new EntityGoalFactory<KillEntityGoal>("kill_creepers", KillEntityGoal.class)
            .withEntityType(EntityType.CREEPER)
            .withMaximum(10)),
    KILL_SKELETONS(new EntityGoalFactory<KillEntityGoal>("kill_skeletons", KillEntityGoal.class)
            .withEntityType(EntityType.SKELETON)
            .withMaximum(10)),
    KILL_VILLAGERS(new EntityGoalFactory<KillEntityGoal>("kill_villagers", KillEntityGoal.class)
            .withEntityType(EntityType.VILLAGER)
            .withMaximum(9)
            .withMinimum(3)),
    KILL_WITHER_SKELETONS(new EntityGoalFactory<KillEntityGoal>("kill_wither_skeletons", KillEntityGoal.class)
            .withEntityType(EntityType.WITHER_SKELETON)
            .withMaximum(10));

    private static final HashMap<String, GoalFactory<?>> GOAL_FACTORIES = new HashMap<String, GoalFactory<?>>();

    static {
        for (Goals g : Goals.values()) {
            GOAL_FACTORIES.put(g.getFactory().getName(), g.getFactory());
        }
    }

    public static Goal createGoal(Game game, String key) {
        GoalFactory<?> factory = GOAL_FACTORIES.get(key);

        return factory.createGoal(game);
    }

    public static Goal loadGoal(Game game, NBTCompound data) {
        GoalFactory<?> factory = GOAL_FACTORIES.get(data.getString("name"));

        return factory.loadGoal(game, data);
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        ArrayList<Goal> goals = new ArrayList<Goal>();

        for (Goals g : Goals.values()) {
            goals.add(g.getFactory().createGoal(game));
        }

        return goals;
    }

    private GoalFactory<?> factory;

    private Goals(GoalFactory<?> factory) {
        this.factory = factory;
    }

    public String getName() {
        return this.factory.getName();
    }

    public GoalFactory<?> getFactory() {
        return this.factory;
    }
}
