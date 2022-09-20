package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class DuplicateAllay extends Goal {
    
    @Override
    public String getTitle() {
        return "Hello Allay";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("allay");
    }

    @Override
    public String getDescription() {
        return "Duplicate an Allay.";
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.ALLAY) return;

        Allay allay = (Allay) event.getRightClicked();
        ItemStack rightClickedStack = event.getPlayer().getInventory().getItem(event.getHand());

        if (!allay.isDancing()
        ||  allay.getInventory().contains(Material.AMETHYST_SHARD)
        ||  rightClickedStack.getType() != Material.AMETHYST_SHARD
        ||  !allay.canDuplicate()
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
    
        setCompletedFor(event.getPlayer());
    }

}
