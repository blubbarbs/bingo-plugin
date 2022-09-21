package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterMesaBiome extends Goal {

    @Override
    public String getTitle() {
        return "Mesa Que Mas Aplauda";
    }

    @Override
    public String getIconPath() {
        return Icons.BLOCK("orange_terracotta");
    }

    @Override
    public String getDescription() {
        return "Enter the Mesa/Badlands Biome.";
    }

    @EventHandler
    public void onExist(PlayerExistEvent event) {
        if (event.getPlayer().getWorld().getBiome(event.getPlayer().getLocation()) != Biome.BADLANDS
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
