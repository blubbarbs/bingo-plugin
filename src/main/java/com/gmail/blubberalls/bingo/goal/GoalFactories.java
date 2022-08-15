package com.gmail.blubberalls.bingo.goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Supplier;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goals.capturable.WearPumpkins;
import com.gmail.blubberalls.bingo.goals.craft.CraftDiamondSword;
import com.gmail.blubberalls.bingo.goals.inventory.PickupDiamonds;
import com.gmail.blubberalls.bingo.goals.kill.KillCreepersGoal;
import com.gmail.blubberalls.bingo.goals.kill.KillSkeletonsGoal;
import com.gmail.blubberalls.bingo.goals.location.EnterStronghold;
import com.gmail.blubberalls.bingo.goals.location.ExploreVillages;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;

public class GoalFactories {
    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();

    public static GoalFactory KILL_CREEPERS = GoalFactories.register("kill_creepers", KillCreepersGoal::new);
    public static GoalFactory KILL_SKELETONS = GoalFactories.register("kill_skeletons", KillSkeletonsGoal::new);
    public static GoalFactory CRAFT_DIAMOND_SWORD = GoalFactories.register("craft_diamond_sword", CraftDiamondSword::new);
    public static GoalFactory PICKUP_DIAMONDS = GoalFactories.register("pickup_diamonds", PickupDiamonds::new);
    public static GoalFactory ENTER_STRONGHOLD = GoalFactories.register("enter_stronghold", EnterStronghold::new);
    public static GoalFactory EXPLORE_VILLAGES = GoalFactories.register("explore_villages", ExploreVillages::new);
    public static GoalFactory WEAR_PUMPKINS = GoalFactories.register("wear_pumpkin", WearPumpkins::new);

    static GoalFactory register(String name, Supplier<Goal> goalConstructor) {        
        GoalFactory factory = new GoalFactory(name, goalConstructor);
        
        GOAL_FACTORIES.put(name, factory);

        return factory;
    }

    public static Goal loadGoal(Game game, NBTCompound savedData) {
        GoalFactory f = GOAL_FACTORIES.get(savedData.getString("name"));

        return f.loadGoal(game, savedData);
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        ArrayList<Goal> goals = new ArrayList<Goal>();

        GOAL_FACTORIES.values().forEach(factory -> goals.add(factory.newGoal(game)));

        return goals;
    }

    public static class GoalFactory {
        private String name;
        private Supplier<Goal> goalConstructor;

        public GoalFactory(String name, Supplier<Goal> goalConstructor) {
            this.name = name;
            this.goalConstructor = goalConstructor;
        }

        public String getName() {
            return name;
        }

        public Goal newGoal(Game game) {
            Goal goal = goalConstructor.get();

            goal.game = game;
            goal.savedData = new NBTContainer();
            goal.initializeNewGoal();
            goal.getSavedData().setString("name", name);

            return goal;
        }    

        public Goal loadGoal(Game game, NBTCompound savedData) {            
            Goal goal = goalConstructor.get();
            
            goal.game = game;
            goal.savedData = new NBTContainer();
            goal.savedData.mergeCompound(savedData);

            return goal;
        }
    }
}
