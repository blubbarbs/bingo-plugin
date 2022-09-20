package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class DyeWolf extends Goal {
    
    @Override
    public String getTitle() {
        return "Steppenwolfe";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("wolf");
    }

    @Override
    public String getDescription() {
        return "Dye a Wolf's collar.";
    }

    public DyeColor getDyeColor(Material material) {
        switch (material) {
        case BONE_MEAL:
            return DyeColor.WHITE;
        case ORANGE_DYE:
            return DyeColor.ORANGE;
        case MAGENTA_DYE:
            return DyeColor.MAGENTA;
        case LIGHT_BLUE_DYE:
            return DyeColor.LIGHT_BLUE;
        case YELLOW_DYE:
            return DyeColor.YELLOW;
        case LIME_DYE:
            return DyeColor.LIME;
        case PINK_DYE:
            return DyeColor.PINK;
        case GRAY_DYE:
            return DyeColor.GRAY;
        case LIGHT_GRAY_DYE:
            return DyeColor.LIGHT_GRAY;
        case CYAN_DYE:
            return DyeColor.CYAN;
        case PURPLE_DYE:
            return DyeColor.PURPLE;
        case BLUE_DYE:
            return DyeColor.BLUE;
        case BROWN_DYE:
            return DyeColor.BROWN;
        case GREEN_DYE:
            return DyeColor.GREEN;
        case RED_DYE:
            return DyeColor.RED;
        case BLACK_DYE:
            return DyeColor.BLACK;
        default:
            return null;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.WOLF
        ||  !((Wolf) event.getRightClicked()).isTamed()
        ||  getDyeColor(event.getPlayer().getInventory().getItem(event.getHand()).getType()) == null
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
