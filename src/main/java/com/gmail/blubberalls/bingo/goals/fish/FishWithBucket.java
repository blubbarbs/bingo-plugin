package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEntityEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class FishWithBucket extends Goal {

    @Override
    public String getTitle() {
        return "Live Fisher";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("cod_bucket");
    }

    @Override
    public String getDescription() {
        return "Capture a fish with a Bucket.";
    }

    @EventHandler
    public void onPlayerBucketEntity(PlayerBucketEntityEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }

}
