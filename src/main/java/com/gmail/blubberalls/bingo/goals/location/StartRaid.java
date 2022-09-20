package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.event.raid.RaidTriggerEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class StartRaid extends Goal {

    @Override
    public String getTitle() {
        return "Raid Brigade";
    }

    @Override
    public String getIconPath() {
        return Icons.MISC("bad_omen");
    }

    @Override
    public String getDescription() {
        return "Start a Raid by entering a village with the Bad Omen effect.";
    }

    
    @EventHandler
    public void onRaidStart(RaidTriggerEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
