package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class DyeSign extends Goal {
    
    @Override
    public String getTitle() {
        return "Sign Language";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("glow_ink_sac");
    }

    @Override
    public String getDescription() {
        return "Dye a Sign with a Glow Ink Sac.";
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        ||  !event.getClickedBlock().getType().name().endsWith("SIGN")
        ||  event.getMaterial() != Material.GLOW_INK_SAC) return;

        setCompletedFor(event.getPlayer());
    }
}
