package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class RenameSheep extends Goal {
    
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getRightClicked().getType() != EntityType.SHEEP
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.NAME_TAG
        ||  !event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getDisplayName().equals("jeb_")) return;

        setCompletedFor(event.getPlayer());
    }

}
