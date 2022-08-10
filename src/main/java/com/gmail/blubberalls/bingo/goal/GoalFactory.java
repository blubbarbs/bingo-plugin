package com.gmail.blubberalls.bingo.goal;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.Goals;
import com.gmail.blubberalls.bingo.util.NBTUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;

public class GoalFactory {
    private String name;
    private int minimumGoal = 1;
    private int maximumGoal = 1;
    private Class<? extends Goal> productClass;
    private HashMap<String, Supplier<?>> dataProducers = new HashMap<String, Supplier<?>>();

    public GoalFactory(String name, Class<? extends Goal> productClass) {
        this.name = name;
        this.productClass = productClass;
    }

    public String getName() {
        return name;
    }

    private GoalFactory with(String key, Object data) {
        dataProducers.put(key, () -> data);

        return this;
    }

    public GoalFactory withEntityType(EntityType type) {
        return with("entity", type.getKey().toString());
    }

    public GoalFactory withMaterial(Material material) {
        return with("material", material.getKey().toString());
    }

    public GoalFactory withStructure(Structure structure) {
        return with("structure", structure.getKey().toString());
    }

    public GoalFactory withGoal(int goal) {
        return with("goal", goal);
    }

    public GoalFactory withMinimum(int minimum) {
        this.minimumGoal = minimum;

        if (dataProducers.get("goal") == null) {
            dataProducers.put("goal", () -> Goals.getRandom().nextInt(minimumGoal, maximumGoal));
        }

        return this;
    }

    public GoalFactory withMaximum(int maximum) {
        this.maximumGoal = maximum;

        if (dataProducers.get("goal") == null) {
            dataProducers.put("goal", () -> Goals.getRandom().nextInt(minimumGoal, maximumGoal));
        }

        return this;
    }

    public Goal loadGoal(Game game, NBTCompound data) {
        try {
            Constructor<? extends Goal> constructor = productClass.getConstructor(Game.class, NBTCompound.class);
            Goal goal = constructor.newInstance(game, data);

            return goal;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Goal createGoal(Game game) {
        Goal goal = loadGoal(game, new NBTContainer());
        NBTCompound goalData = goal.getData();

        Bukkit.getLogger().info(name);

        for (String key : dataProducers.keySet()) {
            Object value = dataProducers.get(key).get();

            NBTUtils.set(goalData, key, value);
            Bukkit.getLogger().info("Serializing " + key + " : " + value);
        }

        goalData.setString("name", name);

        return goal;
    }
}