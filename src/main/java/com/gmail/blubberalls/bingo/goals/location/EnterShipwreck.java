package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterShipwreck extends Goal {
    
    @Override
    public String getTitle() {
        return "Ghost Ship";
    }

    @Override
    public String getIconPath() {
        return Icons.MISC("treasure_map");
    }

    @Override
    public String getDescription() {
        return "Enter a Shipwreck.";
    }    
    
    @EventHandler
    public void onExist(PlayerExistEvent event) {
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());

        if ((at != Structure.SHIPWRECK && at != Structure.SHIPWRECK_BEACHED)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
