package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class Instamine extends Goal {
    
    @Override
    public String getTitle() {
        return "Instamine";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("netherite_pickaxe");
    }

    @Override
    public String getDescription() {
        return "Instamine a block.";
    }

    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        if (!event.getInstaBreak()
        ||  event.getBlock().getType().getHardness() <= 0
        ||  !game.isPlayerPlaying(event.getPlayer())) return;


        setCompletedFor(event.getPlayer());
    }
}
