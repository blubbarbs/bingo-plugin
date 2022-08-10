package com.gmail.blubberalls.bingo.goals;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.goal_types.StructureGoal;
import com.gmail.blubberalls.bingo.util.StructureUtils;

import de.tr7zw.nbtapi.NBTCompound;

public class EnterStructureGoal extends Goal implements StructureGoal {
    private int schedulerID;

    public EnterStructureGoal(Game game, NBTCompound data) {
        super(game, data);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Enter " + " " + getStructureName();
    }

    @Override
    public String getDescription() {
        return "Enter Structure";
    }

    public void updateVisitedStructures() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (StructureUtils.isLocInStructure(p.getLocation(), getStructure())) {
                //Bukkit.getLogger().info("YOU ARE IN THE " + getStructureName() + " STRUCTURE");
            }
        }
    }

    @Override
    public void loadEvents() {
        super.loadEvents();

        this.schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingo.getInstance(), this::updateVisitedStructures , 0L, 10L);
    }

    @Override
    public void unloadEvents() {
        super.unloadEvents();

        Bukkit.getScheduler().cancelTask(schedulerID);
    }


}
