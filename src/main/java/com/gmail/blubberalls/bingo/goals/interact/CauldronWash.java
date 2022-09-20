package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.CauldronLevelChangeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class CauldronWash extends Goal {
    
    @Override
    public String getTitle() {
        return "Laundry Day";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("cauldron");
    }

    @Override
    public String getDescription() {
        return "Wash a dyed item off by putting it in a Cauldron full of water.";
    }

    public boolean isWashingOff(CauldronLevelChangeEvent.ChangeReason reason) {
        switch(reason) {
            case BANNER_WASH:
            case ARMOR_WASH:
            case SHULKER_WASH:
                return true;
            default:
                return false;
        }
    }
    
    @EventHandler
    public void onCauldronChangeLevel(CauldronLevelChangeEvent event) {
        if (!isWashingOff(event.getReason())
        ||  event.getEntity().getType() != EntityType.PLAYER) return;

        Player p = (Player) event.getEntity();

        if (!game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
