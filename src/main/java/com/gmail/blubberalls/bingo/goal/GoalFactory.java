package com.gmail.blubberalls.bingo.goal;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_types.EntityGoal;
import com.gmail.blubberalls.bingo.goal.goal_types.MaterialGoal;
import com.gmail.blubberalls.bingo.goal.goal_types.StructureGoal;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;

public class GoalFactory {
    private String name;
    private Class<? extends Goal> productClass;
    private NBTCompound goalData = new NBTContainer();
    
    public GoalFactory(String name, Class<? extends Goal> productClass) {
        this.name = name;
        this.productClass = productClass;

        goalData.setString("title", TextUtils.capitalizeFirstLetters(name, "_", " "));
        goalData.getOrCreateCompound("goal").setInteger("min", 1);
        goalData.getOrCreateCompound("goal").setInteger("max", 1);
    }
    
    public String getName() {
        return this.getName();
    }
    
    public GoalFactory withTitle(String title) {
        goalData.setString("title", title);

        return this;
    }

    public GoalFactory withIcon(String icon) {
        goalData.setString("icon", icon);

        return this;
    }

    public GoalFactory withMinimum(int minimum) {
        goalData.getOrCreateCompound("goal").setInteger("min", minimum);
    
        return this;
    }
    
    public GoalFactory withMaximum(int maximum) {
        goalData.getOrCreateCompound("goal").setInteger("max", maximum);
    
        return this;
    }
    
    public GoalFactory withGoal(int goal) {
        return withMaximum(goal).withMaximum(goal);
    }
    
    public GoalFactory withStructure(Structure structure) {
        goalData.setString(StructureGoal.KEY, structure.getKey().toString());
    
        return this;
    }
    
    public GoalFactory withEntity(EntityType type) {
        goalData.setString(EntityGoal.KEY, type.getKey().toString());
    
        return this;
    }
    
    public GoalFactory withMaterial(Material material) {
        goalData.setString(MaterialGoal.KEY, material.getKey().toString());
    
        return this;
    }
    
    public Goal loadGoal(Game game, NBTCompound instanceData) {
        try {
            Constructor<? extends Goal> constructor = productClass.getConstructor(Game.class, NBTCompound.class, NBTCompound.class);
            Goal goal = constructor.newInstance(game, goalData, instanceData);
    
            return goal;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    
        return null;
    }
    
    public Goal createGoal(Game game) {
        Goal goal = loadGoal(game, new NBTContainer());
        int minimum = goalData.getCompound("goal").getInteger("min");
        int maximum = goalData.getCompound("goal").getInteger("max");
        int goalNum = GoalFactories.getRandom().nextInt(minimum, maximum + 1);

        goal.getData().setInteger("goal", goalNum);
        goal.getData().setString("name", name);

        return goal;
    }

}