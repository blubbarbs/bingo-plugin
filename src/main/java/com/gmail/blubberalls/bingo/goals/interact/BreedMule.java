package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityBreedEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class BreedMule extends Goal {    
    
    @Override
    public String getTitle() {
        return "Norse";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("mule");
    }

    @Override
    public String getDescription() {
        return "Breed a Horse and a Donkey to create a Mule.";
    }

    public void onEntityBreed(EntityBreedEvent event) {
        if (event.getEntityType() != EntityType.MULE
        ||  event.getBreeder().getType() != EntityType.PLAYER) return;

        Player p = (Player) event.getBreeder();

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
