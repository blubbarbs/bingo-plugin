package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class MineAllOres extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Ores Galore";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("iron_ore");
    }

    @Override
    public String getDescription() {
        return "Mine all ores in the game.";
    }


    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.COPPER_ORE,
            Material.LAPIS_ORE,
            Material.EMERALD_ORE,
            Material.REDSTONE_ORE,
            Material.NETHER_QUARTZ_ORE
        };
    }

    public Material getOre(Material m) {
        switch(m) {
            case DEEPSLATE_COAL_ORE:
                return Material.COAL_ORE;
            case DEEPSLATE_IRON_ORE:
                return Material.IRON_ORE;
            case DEEPSLATE_GOLD_ORE:
            case NETHER_GOLD_ORE:
                return Material.GOLD_ORE;
            case DEEPSLATE_DIAMOND_ORE:
                return Material.DIAMOND_ORE;
            case DEEPSLATE_COPPER_ORE:
                return Material.COPPER_ORE;
            case DEEPSLATE_LAPIS_ORE:
                return Material.LAPIS_ORE;
            case DEEPSLATE_EMERALD_ORE:
                return Material.EMERALD_ORE;
            case DEEPSLATE_REDSTONE_ORE:
                return Material.REDSTONE_ORE;
            default:
                return m;
        }
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {                
        if (event.getItems().size() == 0
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        addUniqueKeyFor(event.getPlayer(), getOre(event.getBlockState().getType()));
    }
}
