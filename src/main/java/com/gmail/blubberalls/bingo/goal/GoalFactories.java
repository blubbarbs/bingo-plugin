package com.gmail.blubberalls.bingo.goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goals.CraftItemGoal;
import com.gmail.blubberalls.bingo.goals.EnterStructureGoal;
import com.gmail.blubberalls.bingo.goals.KillEntityGoal;

import de.tr7zw.nbtapi.NBTCompound;

public class GoalFactories {
    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();
    private static final Random RANDOM = new Random();

    public static GoalFactory KILL_CREEPERS = register("kill_creepers", KillEntityGoal.class)
                                                .withEntity(EntityType.CREEPER)
                                                .withMinimum(5)
                                                .withMaximum(10);
    public static GoalFactory KILL_SKELETONS = register("kill_skeletons", KillEntityGoal.class)
                                                .withEntity(EntityType.SKELETON)
                                                .withMinimum(6)
                                                .withMaximum(7);
    public static GoalFactory CRAFT_DIAMOND_SWORD = register("craft_diamond_sword", CraftItemGoal.class)
                                                    .withMaterial(Material.DIAMOND_SWORD);
    public static GoalFactory ENTER_STRONGHOLD = register("enter_stronghold", EnterStructureGoal.class)
                                                    .withStructure(Structure.STRONGHOLD);

    static GoalFactory register(String name, Class<? extends Goal> productClass) {
        GoalFactory factory = new GoalFactory(name, productClass);
        
        GOAL_FACTORIES.put(name, factory);

        return factory;
    }

    public static Goal loadGoal(Game game, NBTCompound instanceData) {
        GoalFactory factory = GOAL_FACTORIES.get(instanceData.getString("name"));

        return factory != null ? factory.loadGoal(game, instanceData) : null;
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        ArrayList<Goal> goals = new ArrayList<Goal>();

        Bukkit.getLogger().info("did it at least make it here");

        for (GoalFactory f : GOAL_FACTORIES.values()) {
            goals.add(f.createGoal(game));
        }

        return goals;
    }
}
