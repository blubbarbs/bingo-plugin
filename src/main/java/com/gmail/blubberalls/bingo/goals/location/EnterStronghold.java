package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.PlayerExistEvent;

public class EnterStronghold extends Goal {

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        if (Checks.isLocInStructure(event.getPlayer().getLocation(), Structure.STRONGHOLD)) {
            setTeamCompleted(event.getPlayer());
        }        
    }
    
}
