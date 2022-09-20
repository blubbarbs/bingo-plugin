package com.gmail.blubberalls.bingo.goals.interact;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class ReachLevel30 extends Goal {
    
    @Override
    public String getTitle() {
        return "Level Devil";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("experience_bottle");
    }

    @Override
    public String getDescription() {
        return "Reach Level 30.";
    }

    @EventHandler
    public void onLevelUp(PlayerExpChangeEvent event) {
        if (event.getPlayer().getLevel() < 30
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
