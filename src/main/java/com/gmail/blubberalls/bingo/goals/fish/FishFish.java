package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class FishFish extends UniqueKeysGoal {

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
