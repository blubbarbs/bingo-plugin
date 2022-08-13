package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;

import com.gmail.blubberalls.bingo.goal.MovementGoal;
import com.gmail.blubberalls.bingo.util.Checks;

public class EnterStronghold extends MovementGoal {

    @Override
    public void onPlayerLocation(Player p) {
        if (Checks.isLocInStructure(p.getLocation(), Structure.STRONGHOLD)) {
            setCompleted(p);
        }        
    }
    
}
