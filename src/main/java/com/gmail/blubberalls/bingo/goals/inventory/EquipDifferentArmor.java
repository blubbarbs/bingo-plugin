package com.gmail.blubberalls.bingo.goals.inventory;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class EquipDifferentArmor extends Goal {

    public boolean checkDifferentArmor(Player p) {
        HashSet<String> differentArmorTypes = new HashSet<String>();

        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i == null || i.getType().getCreativeCategory() != CreativeCategory.COMBAT) continue;

            String armorMaterial = i.getType().name().split("_")[0];
            differentArmorTypes.add(armorMaterial);
        }

        return differentArmorTypes.size() == 4;
    }

    @EventHandler
    public void onInventoryChange(PlayerInventoryChangedEvent event) {        
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  !checkDifferentArmor(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
