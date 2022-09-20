package com.gmail.blubberalls.bingo.goals.inventory;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.UniqueKeysGoal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerInventoryChangedEvent;

public class CollectFlowers extends UniqueKeysGoal {

    @Override
    public String getTitle() {
        return "Serenity";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("poppy");
    }

    @Override
    public String getDescription() {
        return "Collect 10 different types of Flowers.";
    }

    @Override
    public Keyed[] getValidKeys() {
        return new Keyed[] {
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.ORANGE_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY,
            Material.WITHER_ROSE,
            Material.SUNFLOWER,
            Material.LILAC,
            Material.ROSE_BUSH,
            Material.PEONY
        };
    }

    @Override
    public int getGoal() {
        return 10;
    }

    @EventHandler
    public void onPlayerInventoryUpdate(PlayerInventoryChangedEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())) return;

        event.getCurrent().values().forEach(stack -> addUniqueKeyFor(event.getPlayer(), stack.getType()));
    }
}
