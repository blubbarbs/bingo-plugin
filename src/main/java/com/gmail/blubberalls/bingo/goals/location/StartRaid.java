package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.event.EventHandler;
import org.bukkit.event.raid.RaidTriggerEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class StartRaid extends Goal {
    @EventHandler
    public void onRaidStart(RaidTriggerEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
