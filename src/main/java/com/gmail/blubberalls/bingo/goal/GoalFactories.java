package com.gmail.blubberalls.bingo.goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Supplier;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goals.build.BuildHerobrineAltar;
import com.gmail.blubberalls.bingo.goals.capturable.*;
import com.gmail.blubberalls.bingo.goals.consume.*;
import com.gmail.blubberalls.bingo.goals.craft.*;
import com.gmail.blubberalls.bingo.goals.interact.*;
import com.gmail.blubberalls.bingo.goals.inventory.*;
import com.gmail.blubberalls.bingo.goals.kill.*;
import com.gmail.blubberalls.bingo.goals.location.*;
import com.google.common.collect.HashMultimap;

import de.tr7zw.nbtapi.NBTCompound;

public class GoalFactories {
    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();
    private static final HashMultimap<GoalDifficulty, GoalFactory> GOAL_FACTORIES_BY_DIFFICULTY = HashMultimap.create();

    public static GoalFactory KILL_CREEPERS = GoalFactories.register("kill_creepers", KillCreepers::new);
    public static GoalFactory KILL_SKELETONS = GoalFactories.register("kill_skeletons", KillSkeletons::new);
    public static GoalFactory CRAFT_DIAMOND_SWORD = GoalFactories.register("craft_diamond_sword", CraftDiamondSword::new);
    public static GoalFactory PICKUP_DIAMONDS = GoalFactories.register("pickup_diamonds", PickupDiamonds::new);
    public static GoalFactory ENTER_STRONGHOLD = GoalFactories.register("enter_stronghold", EnterStronghold::new);
    public static GoalFactory EXPLORE_VILLAGES = GoalFactories.register("explore_villages", ExploreVillages::new);
    public static GoalFactory WEAR_PUMPKINS = GoalFactories.register("wear_pumpkin", WearPumpkins::new);
    public static GoalFactory BUILD_HEROBRINE_ALTAR = GoalFactories.register("build_herobrine_altar", BuildHerobrineAltar::new);
    public static GoalFactory EAT_SUSPICIOUS_STEW = GoalFactories.register("eat_suspicious_stew", EatSuspiciousStew::new);
    public static GoalFactory RENAME_RABBIT = GoalFactories.register("rename_rabbit", RenameRabbit::new);
    public static GoalFactory RENAME_SHEEP = GoalFactories.register("rename_sheep", RenameSheep::new);
    public static GoalFactory RENAME_VINDICATOR = GoalFactories.register("rename_vindicator", RenameVindicator::new);
    public static GoalFactory EQUIP_DIFFERENT_ARMOR = GoalFactories.register("equip_different_armor", EquipDifferentArmor::new);
    public static GoalFactory KILL_COOKED_ANIMAL = GoalFactories.register("kill_cooked_mob", KillCookedAnimal::new);

    static GoalFactory register(String name, Supplier<Goal> goalConstructor) {
        GoalFactory factory = new GoalFactory(name, goalConstructor);
        
        GOAL_FACTORIES.put(name, factory);
        // Creates a single, empty instance of the goal just to get its difficulty ;_; sad but necessary
        GOAL_FACTORIES_BY_DIFFICULTY.put(goalConstructor.get().getDifficulty(), factory);

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
            goal.savedData = game.getGoalData().addCompound();
            goal.savedData.setString("name", name);

            return goal;
        }

        public Goal loadGoal(Game game, NBTCompound savedData) {            
            Goal goal = goalConstructor.get();
            
            goal.game = game;
            goal.savedData = savedData;

            return goal;
        }
    }
}
