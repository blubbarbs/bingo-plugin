package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class RenameRabbit extends Goal {

    @Override
    public String getTitle() {
        return "All Toasters Toast";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("name_tag");
    }

    @Override
    public String getDescription() {
        return "Rename a Rabbit to \"Toast\".";
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.RABBIT
        ||  event.getPlayer().getInventory().getItem(event.getHand()).getType() != Material.NAME_TAG
        ||  !event.getPlayer().getInventory().getItem(event.getHand()).getItemMeta().getDisplayName().equals("Toast")
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
