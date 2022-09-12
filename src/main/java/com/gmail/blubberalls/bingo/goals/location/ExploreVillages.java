package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class ExploreVillages extends UniqueKeysGoal {

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Structure.VILLAGE_DESERT,
            Structure.VILLAGE_PLAINS,
            Structure.VILLAGE_SAVANNA,
            Structure.VILLAGE_SNOWY,
            Structure.VILLAGE_TAIGA
        };
    }

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        Player p = event.getPlayer();
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());

        if (!game.isPlayerPlaying(p)) return;
        
        addUniqueKeyFor(p, at);
    }
    
}
