package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectFroglight extends Goal {    
    public boolean isFroglight(ItemStack i) {
        if (i == null) return false;
        
        switch(i.getType()) {
            case OCHRE_FROGLIGHT:
            case VERDANT_FROGLIGHT:
            case PEARLESCENT_FROGLIGHT:
                return true;
            default:
                return false;
        }
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!event.getCurrent().values().stream().anyMatch(this::isFroglight)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
