package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectGrass extends Goal {
    @Override
    public String getTitle() {
        return "Touch Grass";
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !event.getCurrent().values().stream().anyMatch(stack -> stack.getType() == Material.GRASS_BLOCK)) return;
    
        setCompletedFor(event.getPlayer());
    }
}
