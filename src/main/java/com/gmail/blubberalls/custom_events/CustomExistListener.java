package com.gmail.blubberalls.custom_events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class CustomExistListener implements Listener {
    private int existEventSchedulerID = -1;

    public CustomExistListener() {
        existEventSchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingo.getInstance(), this::callExistEvent , 0L, 10L);
    }

    public int getExistSchedulerID() {
        return existEventSchedulerID;
    }

    private void callExistEvent() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerExistEvent event = new PlayerExistEvent(p);

            Bukkit.getPluginManager().callEvent(event);
        }
    }
}
