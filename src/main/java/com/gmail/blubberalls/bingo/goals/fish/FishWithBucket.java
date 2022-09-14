package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class FishWithBucket extends Goal {
    @EventHandler
    public void onPlayerBucketEntity(PlayerBucketEntityEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }

}
