package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterMineshaft extends Goal {
    
    @Override
    public String getTitle() {
        return "Shaft Baft Laft Aft";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("minecart_with_chest");
    }

    @Override
    public String getDescription() {
        return "Enter a Mineshaft.";
    }
    
    @EventHandler
    public void onPlayerExist(PlayerExistEvent event) {
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());
        
        if (at == null
        ||  (at != Structure.MINESHAFT && at != Structure.MINESHAFT_MESA)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());

    }
}
