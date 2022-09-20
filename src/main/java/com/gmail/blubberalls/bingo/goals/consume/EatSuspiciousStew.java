package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class EatSuspiciousStew extends Goal {
    
    @Override
    public String getTitle() {
        return "Sus";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("suspicious_stew");
    }

    @Override
    public String getDescription() {
        return "Eat a Suspicious Stew.";
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.SUSPICIOUS_STEW
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }
}
