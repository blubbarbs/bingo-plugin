package com.gmail.blubberalls.bingo.goals;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.goal_types.EntityGoal;
import com.gmail.blubberalls.bingo.goal.goal_types.NumerableGoal;

import de.tr7zw.nbtapi.NBTCompound;

public class KillEntityGoal extends Goal implements EntityGoal, NumerableGoal {
    public KillEntityGoal(Game game, NBTCompound compound) {
        super(game, compound);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Kill " + getEntityName() + "s";
    }

    @Override
    public String getDescription() {
        return "Kill " + getGoalNumber() + " " + getEntityName() + "(s)!";
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (!isTargetedEntity(event.getEntity())
            || event.getEntity().getKiller() == null
            || isCompleted(event.getEntity().getKiller())) return;

        LivingEntity e = event.getEntity();
        Player killer = e.getKiller();

        addCompletion(killer, 1);
        Bingo.getInstance().getLogger().info("Killed " + e.getName() + ". Count: " + getGoalNumber() + " Completed: " + isCompleted(killer));
    }
}
