package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class ShearPinkSheep extends Goal {

    @Override
    public String getTitle() {
        return "In The Pink";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("pink_sheep");
    }

    @Override
    public String getDescription() {
        return "Shear a Pink Sheep.";
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        if (event.getEntity().getType() != EntityType.SHEEP) return;
    
        Sheep sheep = (Sheep) event.getEntity();
        
        if (sheep.getColor() != DyeColor.PINK
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
