package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.block.Campfire;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.blubberalls.bingo.goal.Goal;

public class UseCampfire extends Goal {
    public boolean isRawFood(Material material) {
        switch(material) {
            case BEEF:
            case CHICKEN:
            case RABBIT:
            case PORKCHOP:
            case MUTTON:
            case COD:
            case SALMON:
            case POTATO:
            case KELP:
                return true;
            default: 
                return false;
        }
    }
    
    public boolean campfireHasFreeSlot(Campfire campfire) {        
        return (campfire.getItem(0) == null
            ||  campfire.getItem(1) == null
            ||  campfire.getItem(2) == null
            ||  campfire.getItem(3) == null);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {        
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        ||  (event.getClickedBlock().getType() != Material.CAMPFIRE && event.getClickedBlock().getType() != Material.SOUL_CAMPFIRE)) return;

        Campfire campfire = (Campfire) event.getClickedBlock().getState();

        if (!campfireHasFreeSlot(campfire)
        ||  !isRawFood(event.getMaterial())
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
