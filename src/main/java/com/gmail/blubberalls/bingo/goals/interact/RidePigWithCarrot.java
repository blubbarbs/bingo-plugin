package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityMountEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class RidePigWithCarrot extends Goal {
 
    @Override
    public String getTitle() {
        return "Fit for A King";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("carrot_on_a_stick");
    }

    @Override
    public String getDescription() {
        return "Ride a Pig with a Carrot on a Stick in hand.";
    }

    @EventHandler
    public void onRide(EntityMountEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER
        ||  event.getMount().getType() != EntityType.PIG) return;

        Player p = (Player) event.getEntity();
        ItemStack main = p.getInventory().getItemInMainHand();
        ItemStack offhand = p.getInventory().getItemInOffHand();

        if ((main.getType() != Material.CARROT_ON_A_STICK && offhand.getType() != Material.CARROT_ON_A_STICK)
        ||  !game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }

    @EventHandler
    public void onChangeItemHeld(PlayerItemHeldEvent event) {
        if (event.getPlayer().getVehicle() == null
        ||  event.getPlayer().getVehicle().getType() != EntityType.PIG) return;

        Player p = event.getPlayer();

        if (p.getInventory().getItem(event.getNewSlot()).getType() != Material.CARROT_ON_A_STICK
        ||  !game.isPlayerPlaying(p)) return;

        setCompletedFor(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (event.getPlayer().getVehicle() == null
        ||  event.getPlayer().getVehicle().getType() != EntityType.PIG) return;

        Player p = event.getPlayer();
        ItemStack main = p.getInventory().getItemInMainHand();
        ItemStack offhand = p.getInventory().getItemInOffHand();

        if ((main.getType() != Material.CARROT_ON_A_STICK && offhand.getType() != Material.CARROT_ON_A_STICK)
        ||  !game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
}
