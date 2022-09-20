package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectTorches extends UniqueKeysGoal {

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.TORCH,
            Material.REDSTONE_TORCH,
            Material.SOUL_TORCH
        };
    }
    
    @EventHandler
    public void onInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        event.getCurrent().values().forEach(stack -> addUniqueKeyFor(event.getPlayer(), stack.getType()));
    }
}
