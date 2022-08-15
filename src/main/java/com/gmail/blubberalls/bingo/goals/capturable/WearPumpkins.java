package com.gmail.blubberalls.bingo.goals.capturable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.CapturableGoal;
import com.gmail.blubberalls.custom_events.InventoryUpdateEvent;

public class WearPumpkins extends CapturableGoal {
    @EventHandler
    public void onInventoryChange(InventoryUpdateEvent event) { 
        Team t = getTeam((Player) event.getView().getPlayer());

        Bukkit.getLogger().info("IS SAME TEAM: " + t.equals(getWhoCompleted()));

        if (isCompleted() && !t.equals(getWhoCompleted())) return;

        Bukkit.getLogger().info("MADE IT");

        for (Player p : getGame().getTeamPlayers(t)) {
            ItemStack helmet = p.getInventory().getHelmet();
            
            Bukkit.getLogger().info(p.getName() +"'S HELMET: " + helmet);

            if (helmet != null && helmet.getType() == Material.CARVED_PUMPKIN) continue;

            setTeamCompleted(t, false);
            return;
        }

        setTeamCompleted(t, true);
    }
}
