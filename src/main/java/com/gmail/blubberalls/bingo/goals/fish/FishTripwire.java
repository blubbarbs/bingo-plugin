package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class FishTripwire extends UniqueKeysGoal {
    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.TRIPWIRE_HOOK,
            Material.STRING
        };
    }
    
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() == null
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        Item i = (Item) event.getCaught();

        addUniqueKeyFor(event.getPlayer(), i.getItemStack().getType());
    }
}
