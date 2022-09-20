package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class FishFish extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Fisher of Fish";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("cod");
    }

    @Override
    public String getDescription() {
        return "Fish the 3 main types of fish.";
    }

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.COD,
            Material.SALMON,
            Material.PUFFERFISH
        };
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() == null
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        Material caught = ((Item) event.getCaught()).getItemStack().getType();

        addUniqueKeyFor(event.getPlayer(), caught);
    }
}
