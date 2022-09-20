package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;

public class WanderingTraderTrade extends Goal {
    
    @Override
    public String getTitle() {
        return "Wander over Yonder";
    }

    @Override
    public String getIconPath() {
        return Icons.ENTITY("wandering_trader");
    }

    @Override
    public String getDescription() {
        return "Trade with a Wandering Trader.";
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null
        ||  !(event.getClickedInventory().getHolder() instanceof WanderingTrader)
        ||  event.getSlotType() != SlotType.RESULT
        ||  event.getCurrentItem() == null
        ||  !Checks.willTakeFromInventory(event.getAction())
        ||  event.getResult() == Result.DENY
        ||  !game.isPlayerPlaying(clicker)) return;

        setCompletedFor(clicker);
    }

}
