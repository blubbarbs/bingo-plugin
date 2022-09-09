package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class ExploreVillages extends UniqueKeysGoal {

    public boolean isVillage(Structure s) {
        return s != null && s.getKey().getKey().startsWith("village");
    }

    @Override
    public int getGoal() {
        return 3;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        return super.getProgressDescriptionFor(t).replaceAll("Unique Keys", "Villages Explored");
    }

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        Player p = event.getPlayer();
        Structure at = Checks.getStructureAtLoc(event.getPlayer().getLocation());

        if (!game.isPlayerPlaying(p)
        ||  !isVillage(at)
        ||  containsUniqueKeyFor(p, at)) return;
        
        addUniqueKeyFor(p, at);
    }
    
}
