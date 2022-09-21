package com.gmail.blubberalls.bingo.goal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goals.attack.*;
import com.gmail.blubberalls.bingo.goals.build.*;
import com.gmail.blubberalls.bingo.goals.capturable.*;
import com.gmail.blubberalls.bingo.goals.consume.*;
import com.gmail.blubberalls.bingo.goals.craft.*;
import com.gmail.blubberalls.bingo.goals.fish.*;
import com.gmail.blubberalls.bingo.goals.interact.*;
import com.gmail.blubberalls.bingo.goals.inventory.*;
import com.gmail.blubberalls.bingo.goals.location.*;
import com.gmail.blubberalls.bingo.goals.mine.*;
import com.google.common.collect.ArrayListMultimap;

import de.tr7zw.nbtapi.NBTCompound;

public class Goals {
    private static final HashMap<String, GoalFactory> GOAL_FACTORIES = new HashMap<String, GoalFactory>();
    private static final ArrayListMultimap<GoalDifficulty, GoalFactory> GOAL_FACTORIES_BY_DIFFICULTY = ArrayListMultimap.create();

    static {
        registerEasyGoals();
        registerMediumGoals();
        registerHardGoals();
        registerCapturableGoals();
    }

    static void registerEasyGoals() {
        registerEasy(EatSuspiciousStew.class);
        registerEasy(KillCookedAnimal.class);
        registerEasy(CollectFullCobblestone.class);
        registerEasy(EnterLushCave.class);
        registerEasy(BuildIdiotBox.class);
        registerEasy(DieByFallingAnvil.class);
        registerEasy(UseCampfire.class);
        registerEasy(AttackPlayerWithFishingRod.class);
        registerEasy(SuicideByArrow.class);
        registerEasy(PlacePainting.class);
        registerEasy(EatGoldenCarrot.class);
        registerEasy(EatPufferfish.class);
        registerEasy(FishWithBucket.class);
        registerEasy(LeatherHorseArmor.class);
        registerEasy(KillByStalagmite.class);
        registerEasy(LlamaCarpet.class);
        registerEasy(DyeWolf.class);
        registerEasy(EatHoneyBottle.class);
        registerEasy(FishFish.class);
        registerEasy(SmeltSmoothStone.class);
        registerEasy(CollectBuckets.class);
        registerEasy(UseSmoker.class);
        registerEasy(TameCat.class);
        registerEasy(CauldronWash.class);
        registerEasy(CraftPumpkinPie.class);
        registerEasy(CraftDriedKelpBlock.class);
        registerEasy(EatBreadAndFish.class);
        registerEasy(KillZombieWithShovel.class);
        registerEasy(PlaceCandleOnCake.class);
        registerEasy(DyeSign.class);
    }

    static void registerMediumGoals() {
        registerMedium(RenameVindicator.class);
        registerMedium(RenameRabbit.class);
        registerMedium(RenameSheep.class);
        registerMedium(RenameToDinnerbone.class);
        registerMedium(ExploreVillages.class);
        registerMedium(BuildHerobrineAltar.class);
        registerMedium(MineAmethystCrystal.class);
        registerMedium(KillZombies.class);
        registerMedium(KillCreeperWCreeper.class);
        registerMedium(KillPhantom.class);
        registerMedium(EatRottenFlesh.class);
        registerMedium(KillSkeletons.class);
        registerMedium(FertilizeCrops.class);
        registerMedium(CollectFlowers.class);
        registerMedium(CraftDoors.class);
        registerMedium(RidePigWithCarrot.class);
        registerMedium(IgniteTNTFlamingArrow.class);
        registerMedium(KillBlaze.class);
        registerMedium(KillBoA.class);
        registerMedium(KillPolarBear.class);
        registerMedium(ReachLevel30.class);
        registerMedium(ShearPinkSheep.class);
        registerMedium(EquipChainmailArmor.class);
        registerMedium(EquipDifferentArmor.class);
        registerMedium(PlayerTeleportFar.class);
        registerMedium(MineMonsterSpawner.class);
        registerMedium(EnterShipwreck.class);
        registerMedium(FishTripwire.class);
        registerMedium(CompleteVillagerTrades.class);
        registerMedium(Instamine.class);
        registerMedium(TradeWithPiglins.class);
        registerMedium(CollectTorches.class);
        registerMedium(EnterMesaBiome.class);
        registerMedium(EnterMineshaft.class);
    }

    static void registerHardGoals() {
        registerHard(CollectGrass.class);
        registerHard(HaveParrotOnShoulder.class);
        registerHard(MineAllOres.class);
        registerHard(PlayerVelocityFast.class);
        registerHard(PlayerVelocitySlow.class);
        registerHard(PlantTrees.class);
        registerHard(DestroyDiamonds.class);
        registerHard(KillDrowned.class);
        registerHard(KillEndermite.class);
        registerHard(BreakNetheriteHoe.class);
        registerHard(DuplicateAllay.class);
        registerHard(MooshroomSoup.class);
        registerHard(FishTropicalFish.class);
        registerHard(CollectFroglight.class);
        registerHard(EnterStronghold.class);
        registerHard(WanderingTraderTrade.class);
        registerHard(KillZoglin.class);
        registerHard(CraftMagmaBlock.class);
        registerHard(BreedMule.class);
        registerHard(PlaceWetSponge.class);
        registerHard(SplashWitch.class);
        registerHard(StartRaid.class);
    }

    static void registerCapturableGoals() {
        registerCapturable(KillMostPlayers.class);
        registerCapturable(WearPumpkins.class);
        registerCapturable(EatMostFood.class);
        registerCapturable(MineMostBlocks.class);
    }

    static GoalFactory registerEasy(Class<? extends Goal> goalClass) {
        return register(goalClass, GoalDifficulty.EASY);
    }

    static GoalFactory registerMedium(Class<? extends Goal> goalClass) {
        return register(goalClass, GoalDifficulty.MEDIUM);
    }

    static GoalFactory registerHard(Class<? extends Goal> goalClass) {
        return register(goalClass, GoalDifficulty.HARD);
    }

    static GoalFactory registerCapturable(Class<? extends Goal> goalClass) {
        return register(goalClass, GoalDifficulty.CAPTURABLE);
    }

    static GoalFactory register(Class<? extends Goal> goalClass, GoalDifficulty difficulty) {
        GoalFactory factory = new GoalFactory(goalClass, difficulty);
        
        GOAL_FACTORIES.put(factory.getName(), factory);
        GOAL_FACTORIES_BY_DIFFICULTY.put(difficulty, factory);

        return factory;
    }

    public static Goal loadGoal(Game game, NBTCompound savedData) {
        GoalFactory f = GOAL_FACTORIES.get(savedData.getString("name"));

        return f.loadGoal(game, savedData);
    }

    public static Collection<Goal> randomGoals(Game game, int amount) {
        List<GoalFactory> easyGoals = GOAL_FACTORIES_BY_DIFFICULTY.get(GoalDifficulty.EASY);
        List<GoalFactory> medGoals = GOAL_FACTORIES_BY_DIFFICULTY.get(GoalDifficulty.MEDIUM);
        List<GoalFactory> hardGoals = GOAL_FACTORIES_BY_DIFFICULTY.get(GoalDifficulty.HARD);
        List<GoalFactory> captGoals = GOAL_FACTORIES_BY_DIFFICULTY.get(GoalDifficulty.CAPTURABLE);
        ArrayList<Goal> goals = new ArrayList<Goal>();

        for (int i = 0; i < 8; i++) {
            GoalFactory f = easyGoals.get(game.getRandom().nextInt(easyGoals.size()));

            goals.add(f.newGoal(game));
            easyGoals.remove(f);
        }

        for (int i = 0; i < 8; i++) {
            GoalFactory f = medGoals.get(game.getRandom().nextInt(medGoals.size()));

            goals.add(f.newGoal(game));
            medGoals.remove(f);
        }

        for (int i = 0; i < 8; i++) {
            GoalFactory f = hardGoals.get(game.getRandom().nextInt(hardGoals.size()));

            goals.add(f.newGoal(game));
            hardGoals.remove(f);
        }

        for (int i = 0; i < 1; i++) {
            GoalFactory f = captGoals.get(game.getRandom().nextInt(captGoals.size()));

            goals.add(f.newGoal(game));
            captGoals.remove(f);
        }

        return goals;
    }

    public static class GoalFactory {
        private Class<? extends Goal> goalClass;
        private GoalDifficulty difficulty = GoalDifficulty.EASY;

        public GoalFactory(Class<? extends Goal> goalClass, GoalDifficulty difficulty) {
            this.goalClass = goalClass;
            this.difficulty = difficulty;
        }

        public String getName() {
            String name = "";
            String className = goalClass.getSimpleName();

            for (int i = 0; i < className.length(); i++) {
                char c = className.charAt(i);

                name += Character.isUpperCase(c) && i > 0 ? '_' + Character.toString(Character.toLowerCase(c)) : Character.toString(Character.toLowerCase(c));
            }

            return name;
        }

        public Goal newGoal(Game game) {
            try {
                Goal goal = goalClass.getConstructor().newInstance();

                goal.game = game;
                goal.difficulty = difficulty;
                goal.savedData = game.getGoalData().addCompound();
                goal.savedData.setString("name", getName());
    
                return goal;                    
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        public Goal loadGoal(Game game, NBTCompound savedData) {            
            try {
                Goal goal = goalClass.getConstructor().newInstance();

                goal.game = game;
                goal.difficulty = difficulty;
                goal.savedData = savedData;
        
                return goal;                    
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
