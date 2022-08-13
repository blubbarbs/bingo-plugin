package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import net.minecraft.world.inventory.ICrafting;

public abstract class InventoryGoal extends Goal {    
    private ICrafting nmsSlotListener = new ICrafting() {
        // On slot changed
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {            
            Inventory updatedInventory = nmsContainer.getBukkitView().getInventory(rawSlot);
            int slot = nmsContainer.getBukkitView().convertSlot(rawSlot);
            ItemStack stack = CraftItemStack.asBukkitCopy(nmsStack);

            if (updatedInventory.getType() == InventoryType.PLAYER) {
                onInventoryChange((Player) updatedInventory.getHolder(), stack, slot);
            }
        }

        // On data change (unused)
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, int data) {}
    };

    private void unregisterInventoryListener(InventoryView view) {
        if (nmsSlotListener == null) return;
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener
        craftView.getHandle().b(nmsSlotListener);
    }

    private void registerInventoryListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        registerInventoryListener(event.getPlayer().getOpenInventory());
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.PLAYER 
            || event.getInventory().getType() == InventoryType.CREATIVE
            || event.getInventory().getType() == InventoryType.CRAFTING) return;
        
        registerInventoryListener(event.getView());
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.PLAYER 
            || event.getInventory().getType() == InventoryType.CREATIVE
            || event.getInventory().getType() == InventoryType.CRAFTING) return;
        
        unregisterInventoryListener(event.getView());
    }

    @Override
    public void loadEvents() {
        super.loadEvents();
        
        Bukkit.getOnlinePlayers().forEach(p -> registerInventoryListener(p.getOpenInventory()));
    }

    @Override
    public void unloadEvents() {
        super.unloadEvents();

        Bukkit.getOnlinePlayers().forEach(p -> unregisterInventoryListener(p.getOpenInventory()));
    }

    public abstract void onInventoryChange(Player player, ItemStack newStack, int changedSlot);

}
