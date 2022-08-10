package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.GoalFactory;
import com.gmail.blubberalls.bingo.goals.CraftItemGoal;
import com.gmail.blubberalls.bingo.goals.EnterStructureGoal;
import com.gmail.blubberalls.bingo.goals.KillEntityGoal;

import de.tr7zw.nbtapi.NBTCompound;

public enum Goals {

    KILL_CREEPERS(new GoalFactory("kill_creepers", KillEntityGoal.class)
            .withEntityType(EntityType.CREEPER)
            .withMaximum(10)),
    KILL_SKELETONS(new GoalFactory("kill_skeletons", KillEntityGoal.class)
            .withEntityType(EntityType.SKELETON)
            .withMaximum(10)),
    KILL_VILLAGERS(new GoalFactory("kill_villagers", KillEntityGoal.class)
            .withEntityType(EntityType.VILLAGER)
            .withMaximum(9)
            .withMinimum(3)),
    KILL_WITHER_SKELETONS(new GoalFactory("kill_wither_skeletons", KillEntityGoal.class)
            .withEntityType(EntityType.WITHER_SKELETON)
            .withMaximum(10)),
    CRAFT_DIAMOND_PICKAXE(new GoalFactory("craft_diamond_pickaxe", CraftItemGoal.class)
            .withMaterial(Material.DIAMOND_PICKAXE)
            .withMaximum(2)),
    ENTER_IGLOO(new GoalFactory("enter_igloo", EnterStructureGoal.class)
            .withStructure(Structure.IGLOO)),
    ENTER_SHIPWRECK(new GoalFactory("enter_shipwreck", EnterStructureGoal.class)
            .withStructure(Structure.SHIPWRECK)),
    ENTER_JUNGLE_PYRAMID(new GoalFactory("enter_jungle_pyramid", EnterStructureGoal.class)
            .withStructure(Structure.JUNGLE_PYRAMID)),
    ENTER_PILLAGER_OUTPOST(new GoalFactory("enter_pillager_outpost", EnterStructureGoal.class)
            .withStructure(Structure.PILLAGER_OUTPOST));

    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();
    private static final Random RANDOM = new Random();

    static {
        for (Goals g : Goals.values()) {
            GOAL_FACTORIES.put(g.getFactory().getName(), g.getFactory());
        }
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public static Goal createGoal(Game game, String key) {
        GoalFactory factory = GOAL_FACTORIES.get(key);

        return factory.createGoal(game);
    }

    public static Goal loadGoal(Game game, NBTCompound data) {
        GoalFactory factory = GOAL_FACTORIES.get(data.getString("name"));

        return factory.loadGoal(game, data);
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        ArrayList<Goal> goals = new ArrayList<Goal>();

        for (Goals g : Goals.values()) {
            goals.add(g.getFactory().createGoal(game));
        }

        return goals;
    }

    private GoalFactory factory;

    private Goals(GoalFactory factory) {
        this.factory = factory;
    }

    public String getName() {
        return this.factory.getName();
    }

    public GoalFactory getFactory() {
        return this.factory;
    }
}
