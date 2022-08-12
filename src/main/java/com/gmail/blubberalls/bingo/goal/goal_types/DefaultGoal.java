package com.gmail.blubberalls.bingo.goal.goal_types;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;
import net.md_5.bungee.api.ChatColor;

public interface DefaultGoal extends Listener {
    public Game getGame();
    public NBTCompound getData();
    public NBTCompound getGoalData();
    public String getName();
    public int getGoal();

    public String getTooltip();
    public String getIcon();
    public String getDescription();
    public String getTitle();

    public int getCompletion(Player p);    
    public boolean isCompleted(Player p);
    public boolean isSubscribed(Player p);
    public NBTCompound getTeamData(Player p);

    public void setCompletion(Player p, int completion);
    public void addCompletion(Player p, int delta);
    public void setCompleted(Player p);
    public void setSubscription(Player p, boolean subscribe);

    public void loadEvents();
    public void unloadEvents();

    default String getCompletionStatus(Player p) {
        return ChatColor.GREEN + getTitle();
    }
}
