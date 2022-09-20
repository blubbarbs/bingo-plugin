package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class MooshroomSoup extends Goal {
    
    @Override
    public String getTitle() {
        return "Shroom Shake";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("mushroom_stew");
    }

    @Override
    public String getDescription() {
        return "Right click a Mooshroom with an Empty Bowl.";
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.MUSHROOM_COW
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.BOWL
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
