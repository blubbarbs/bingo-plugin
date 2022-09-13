package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class PlantTrees extends UniqueKeysGoal {
    
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.OAK_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.BIRCH_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.ACACIA_SAPLING,
            Material.DARK_OAK_SAPLING,
            Material.CRIMSON_FUNGUS,
            Material.WARPED_FUNGUS
        };
    }

    public Material getSapling(TreeType treeType) {
        switch(treeType) {
            case TREE:
                return Material.OAK_SAPLING;
            case REDWOOD:
                return Material.SPRUCE_SAPLING;
            case BIRCH:
                return Material.BIRCH_SAPLING;
            case SMALL_JUNGLE:
            case JUNGLE:
            case COCOA_TREE:
                return Material.JUNGLE_SAPLING;
            case ACACIA:
                return Material.ACACIA_SAPLING;
            case DARK_OAK:
                return Material.DARK_OAK_SAPLING;
            case CRIMSON_FUNGUS:
                return Material.CRIMSON_FUNGUS;
            case WARPED_FUNGUS:
                return Material.WARPED_FUNGUS;
            default:
                return null;
        }
    }

    @Override
    public String getTitle() {
        return "Tree Hugger";
    }

    @Override
    public String getDescription() {
        return "Plant and grow all of the sapling types with bone meal.";
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !event.isFromBonemeal()
        ||  getSapling(event.getSpecies()) == null) return;

        Material sapling = getSapling(event.getSpecies());

        if (containsUniqueKeyFor(event.getPlayer(), sapling)) return;

        addUniqueKeyFor(event.getPlayer(), sapling);
    }

}
