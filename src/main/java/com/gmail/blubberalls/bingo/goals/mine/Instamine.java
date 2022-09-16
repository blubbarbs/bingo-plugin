package com.gmail.blubberalls.bingo.goals.mine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class Instamine extends Goal {
    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        if (!event.getInstaBreak()
        ||  event.getBlock().getType().getHardness() <= 0
        ||  !game.isPlayerPlaying(event.getPlayer())) return;


        setCompletedFor(event.getPlayer());
    }
}
