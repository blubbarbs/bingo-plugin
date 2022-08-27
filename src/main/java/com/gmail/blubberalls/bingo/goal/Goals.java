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
import com.gmail.blubberalls.bingo.goals.fish.*;
import com.gmail.blubberalls.bingo.goals.interact.*;
import com.gmail.blubberalls.bingo.goals.inventory.*;
import com.gmail.blubberalls.bingo.goals.kill.*;
import com.gmail.blubberalls.bingo.goals.location.*;
import com.gmail.blubberalls.bingo.goals.mine.MineAmethystCrystal;
import com.google.common.collect.HashMultimap;

import de.tr7zw.nbtapi.NBTCompound;

public class Goals {
    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();
    private static final HashMultimap<GoalDifficulty, GoalFactory> GOAL_FACTORIES_BY_DIFFICULTY = HashMultimap.create();

    static {
        registerEasyGoals();
        registerMediumGoals();
        registerHardGoals();
    }

    static void registerEasyGoals() {
        registerEasy("kill_creepers", KillCreepers::new);
        registerEasy("kill_skeletons", KillSkeletons::new);
        registerEasy("craft_diamond_sword", CraftDiamondSword::new);
        registerEasy("pickup_diamonds", PickupDiamonds::new);
        registerEasy("enter_stronghold", EnterStronghold::new);
        
        registerEasy("eat_suspicious_stew", EatSuspiciousStew::new);
        registerEasy("equip_different_armor", EquipDifferentArmor::new);
        registerEasy("kill_cooked_mob", KillCookedAnimal::new);
        registerEasy("get_full_cobble", CollectFullCobblestone::new);
        registerEasy("fish_fishing_rod", FishFishingRod::new);
        registerEasy("fish_lilypad", FishLilypad::new);
        registerEasy("fish_nautilus_shell", FishNautilusShell::new);
        registerEasy("fish_tropical_fish", FishTropicalFish::new);
        registerEasy("dye_wolf", DyeWolf::new);
        registerEasy("shear_pink_sheep", ShearPinkSheep::new);
        registerEasy("enter_lush_cave", EnterLushCave::new);
    }

    static void registerMediumGoals() {
        registerMedium("rename_vindicator", RenameVindicator::new);
        registerMedium("rename_rabbit", RenameRabbit::new);
        registerMedium("rename_sheep", RenameSheep::new);
        registerMedium("explore_villages", ExploreVillages::new);
        registerMedium("build_herobrine_altar", BuildHerobrineAltar::new);
        registerMedium("craft_magma_block", CraftMagmaBlock::new);
        registerMedium("mine_amethyst", MineAmethystCrystal::new);
    }

    static void registerHardGoals() {
        registerHard("wear_pumpkin", WearPumpkins::new);
        registerHard("kill_phantom", KillPhantom::new);
    }

    static GoalFactory registerEasy(String name, Supplier<Goal> goalConstructor) {
        return register(name, goalConstructor, GoalDifficulty.EASY);
    }

    static GoalFactory registerMedium(String name, Supplier<Goal> goalConstructor) {
        return register(name, goalConstructor, GoalDifficulty.MEDIUM);
    }

    static GoalFactory registerHard(String name, Supplier<Goal> goalConstructor) {
        return register(name, goalConstructor, GoalDifficulty.HARD);
    }

    static GoalFactory register(String name, Supplier<Goal> goalConstructor, GoalDifficulty difficulty) {
        GoalFactory factory = new GoalFactory(name, goalConstructor, difficulty);
        
        GOAL_FACTORIES.put(name, factory);
        GOAL_FACTORIES_BY_DIFFICULTY.put(difficulty, factory);

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
        private GoalDifficulty difficulty = GoalDifficulty.EASY;
        private Supplier<Goal> goalConstructor;

        public GoalFactory(String name, Supplier<Goal> goalConstructor, GoalDifficulty difficulty) {
            this.name = name;
            this.difficulty = difficulty;
            this.goalConstructor = goalConstructor;
        }

        public String getName() {
            return name;
        }

        public Goal newGoal(Game game) {
            Goal goal = goalConstructor.get();

            goal.game = game;
            goal.difficulty = difficulty;
            goal.savedData = game.getGoalData().addCompound();
            goal.savedData.setString("name", name);

            return goal;
        }

        public Goal loadGoal(Game game, NBTCompound savedData) {            
            Goal goal = goalConstructor.get();
            
            goal.game = game;
            goal.difficulty = difficulty;
            goal.savedData = savedData;

            return goal;
        }
    }
}
