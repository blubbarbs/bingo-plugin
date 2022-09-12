package com.gmail.blubberalls.bingo.goals.fish;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;

public class FishAllFish extends UniqueKeysGoal {

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.COD,
            Material.TROPICAL_FISH,
            Material.SALMON,
            Material.PUFFERFISH
        };
    }


    public boolean isFish(Item i) {
        switch(i.getItemStack().getType()) {
            case COD:
            case TROPICAL_FISH:
            case SALMON:
            case PUFFERFISH:
                return true;
            default:
                return false;
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getCaught().getType() != EntityType.DROPPED_ITEM
        ||  !isFish((Item) event.getCaught())) return;

        Material caught = ((Item) event.getCaught()).getItemStack().getType();

        if (containsUniqueKeyFor(event.getPlayer(), caught)) return;

        addCompletionFor(event.getPlayer(), 1);
    }
}
