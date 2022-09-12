package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.ScoredCapturableGoal;

public class EatMostFood extends ScoredCapturableGoal {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        ||  (event.getClickedBlock().getType() != Material.CAKE && event.getClickedBlock().getType() != Material.CANDLE_CAKE) 
        ||  !game.isPlayerPlaying(event.getPlayer())
        ||  event.getPlayer().getFoodLevel() == 20) return;
    
        addCompletionFor(event.getPlayer(), 1);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !event.getItem().getType().isEdible()) return;

        addCompletionFor(event.getPlayer(), 1);
    }

}
