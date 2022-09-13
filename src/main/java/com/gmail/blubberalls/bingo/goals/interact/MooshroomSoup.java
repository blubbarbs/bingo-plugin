package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class MooshroomSoup extends Goal {
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.MUSHROOM_COW
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.BOWL
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
