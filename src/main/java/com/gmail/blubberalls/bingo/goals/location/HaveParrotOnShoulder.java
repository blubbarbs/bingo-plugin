package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

@SuppressWarnings("deprecation")
public class HaveParrotOnShoulder extends Goal {

    @Override
    public String getTitle() {
        return "You are a Pirate";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("red_parrot");
    }

    @Override
    public String getDescription() {
        return "Have a Parrot on your shoulder.";
    }

    public boolean hasParrot(Player p) {
        return (p.getShoulderEntityLeft() != null && p.getShoulderEntityLeft().getType() == EntityType.PARROT)
        ||     (p.getShoulderEntityRight() != null && p.getShoulderEntityRight().getType() == EntityType.PARROT);
    } 

    @EventHandler
    public void onExist(PlayerExistEvent event) {
        if (!hasParrot(event.getPlayer())
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
