package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Checks;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class EnterStronghold extends Goal {

    @Override
    public String getTitle() {
        return "Stronglehold";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("ender_eye");
    }

    @Override
    public String getDescription() {
        return "Enter a Stronghold.";
    }

    @EventHandler
    public void onPlayerLocation(PlayerExistEvent event) {
        Player p = event.getPlayer();

        if (!Checks.isLocInStructure(p.getLocation(), Structure.STRONGHOLD)
        ||  !game.isPlayerPlaying(p)) return;

        setCompletedFor(p);
    }
    
}