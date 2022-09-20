package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.block.data.type.Cake;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class PlaceCandleOnCake extends Goal {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {                
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        ||  event.getClickedBlock().getType() != Material.CAKE
        ||  !event.getMaterial().name().endsWith("CANDLE")) return;

        Cake cake = (Cake) event.getClickedBlock().getBlockData();

        if (cake.getBites() > 0) return;

        setCompletedFor(event.getPlayer());
    }
}
