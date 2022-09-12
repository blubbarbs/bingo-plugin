package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectFroglight extends Goal {
    public boolean inventoryContainsFroglight(Player p) {
        for (ItemStack i : p.getInventory().getContents()) {
            if (i == null) continue;
            if (i.getType() == Material.OCHRE_FROGLIGHT || i.getType() == Material.VERDANT_FROGLIGHT || i.getType() == Material.PEARLESCENT_FROGLIGHT) return true;
        }

        return false;
    }
    
    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !inventoryContainsFroglight(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
