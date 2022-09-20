package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class RenameToDinnerbone extends Goal {

    @Override
    public String getTitle() {
        return "This Side Up";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("name_tag");
    }

    @Override
    public String getDescription() {
        return "Rename any mob to \"Dinnerbone\".";
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.NAME_TAG
        ||  !event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getDisplayName().equals("Dinnerbone")
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
