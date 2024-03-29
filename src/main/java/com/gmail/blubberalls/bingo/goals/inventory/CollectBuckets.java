package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectBuckets extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Lord Buckethead";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("bucket");
    }

    @Override
    public String getDescription() {
        return "Fill a Bucket with all types of liquids.";
    }

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.WATER_BUCKET,
            Material.LAVA_BUCKET,
            Material.MILK_BUCKET,
            Material.POWDER_SNOW_BUCKET
        };
    }
    
    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        event.getCurrent().values().forEach(stack -> addUniqueKeyFor(event.getPlayer(), stack.getType()));
    }

}
