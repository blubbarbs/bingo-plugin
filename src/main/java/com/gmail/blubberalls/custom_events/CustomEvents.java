package com.gmail.blubberalls.custom_events;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.gmail.blubberalls.bingo.Bingo;

import net.minecraft.world.inventory.ICrafting;

public class CustomEvents implements Listener {
    private static int existEventSchedulerID;
    private static ICrafting nmsSlotListener = new ICrafting() {
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, net.minecraft.world.item.ItemStack nmsStack) {            
            Inventory updatedInventory = nmsContainer.getBukkitView().getInventory(rawSlot);
            int slot = nmsContainer.getBukkitView().convertSlot(rawSlot);
            ItemStack stack = CraftItemStack.asBukkitCopy(nmsStack);

            if (updatedInventory.getType() == InventoryType.PLAYER) {
                InventoryUpdateEvent event = new InventoryUpdateEvent(nmsContainer.getBukkitView(), stack, slot);

                Bukkit.getPluginManager().callEvent(event);
            }
        }

        // On data change (unused)
        @Override
        public void a(net.minecraft.world.inventory.Container nmsContainer, int rawSlot, int data) {}
    };

    static {
        existEventSchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingo.getInstance(), new Runnable() {

            @Override
            public void run() {                
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerExistEvent event = new PlayerExistEvent(p);

                    Bukkit.getPluginManager().callEvent(event);
                }
            }
            
        }, 0L, 10L);
    }

    public static int getExistSchedulerID() {
        return existEventSchedulerID;
    }

    public static void registerInventoryListener(InventoryView view) {
        CraftInventoryView craftView = (CraftInventoryView) view;

        // Unregister slot listener (if there)
        craftView.getHandle().b(nmsSlotListener);
        // Register slot listener
        craftView.getHandle().a(nmsSlotListener);
    }
}
