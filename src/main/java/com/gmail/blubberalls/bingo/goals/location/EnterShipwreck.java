package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterShipwreck extends Goal {
    @EventHandler
    public void onExist(PlayerExistEvent event) {
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());

        if ((at != Structure.SHIPWRECK && at != Structure.SHIPWRECK_BEACHED)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
