package com.gmail.blubberalls.bingo.goals;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.EntityGoal;
import com.gmail.blubberalls.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;

public class KillEntityGoal extends EntityGoal {
    public KillEntityGoal(Game game, NBTCompound compound) {
        super(game, compound);
    }

    @Override
    public String getTitle() {
        return "Kill " + TextUtils.captailizeFirstLetter(getEntityName());
    }

    @Override
    public String getDescription() {
        return "Kill " + getGoalNumber() + " " + getEntityName() + "(s)!";
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (!isTargetedEntity(event.getEntity())
            || event.getEntity().getKiller() == null) return;

        LivingEntity e = event.getEntity();
        Player killer = e.getKiller();

        addCompletion(killer, 1);

        Bingo.getInstance().getLogger().info("Killed " + e.getName() + ". Count: " + getGoalNumber() + " Completed: " + isCompleted(killer));
    }

}
