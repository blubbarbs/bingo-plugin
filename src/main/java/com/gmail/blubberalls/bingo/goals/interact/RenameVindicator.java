package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class RenameVindicator extends Goal {

    @Override
    public String getTitle() {
        return "Here's ____!";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("name_tag");
    }

    @Override
    public String getDescription() {
        return "Rename a Vindicator to \"Johnny\".";
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VINDICATOR
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.NAME_TAG
        ||  !event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getDisplayName().equals("Johnny")
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }

}
