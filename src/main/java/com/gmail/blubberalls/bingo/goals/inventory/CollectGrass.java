package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectGrass extends Goal {
    
    @Override
    public String getTitle() {
        return "Touch Grass";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("grass_block");
    }

    @Override
    public String getDescription() {
        return "Obtain a Grass Block";
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!event.getCurrent().values().stream().anyMatch(stack -> stack.getType() == Material.GRASS_BLOCK)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
    
        setCompletedFor(event.getPlayer());
    }
}
