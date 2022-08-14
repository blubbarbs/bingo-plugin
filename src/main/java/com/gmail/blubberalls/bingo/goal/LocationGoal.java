package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.Bingo;

public abstract class LocationGoal extends Goal {
    private int scheduleID;

    @Override
    public void loadEvents() {
        super.loadEvents();

        this.scheduleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingo.getInstance(), new Runnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> onPlayerLocation(p));
            }

        }, 0L, 10L);
    }

    @Override
    public void unloadEvents() {
        super.unloadEvents();

        Bukkit.getScheduler().cancelTask(this.scheduleID);
    }

    public abstract void onPlayerLocation(Player p);
}
