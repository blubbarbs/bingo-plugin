package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class EquipChainmailArmor extends Goal {
    public boolean isChainmailArmor(ItemStack stack) {
        switch(stack.getType()) {
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_HELMET:
                return true;
            default:
                return false;
        }
    }
    
    public boolean hasEquippedChainmailArmor(Player p) {
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && isChainmailArmor(i)) {
                return true;
            } 
        }

        return false;
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!hasEquippedChainmailArmor(event.getPlayer())
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
