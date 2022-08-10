package com.gmail.blubberalls.bingo.goal.goal_types;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;
import net.md_5.bungee.api.ChatColor;

public interface DefaultGoal extends Listener {
    public String getIcon();
    public String getDescription();
    public String getTitle();
    public Game getGame();
    public NBTCompound getData();
    
    default String getName() {
        return getData().getString("name");
    }

    default String getTooltip() {
        return getTitle() + "\n" + getDescription();
    }

    default int getGoalNumber() {
        return getData().hasKey("goal") ? getData().getInteger("goal") : 1;
    }

    default int getCompletion(Player p) {
        return getTeamData(p).getInteger("completion");
    }

    default String getCompletionStatus(Player p) {
        return ChatColor.GREEN + getTitle();
    }

    default boolean isSubscribed(Player p) {
        return !isCompleted(p) && true;
    }

    default boolean isCompleted(Player p) {
        return getCompletion(p) >= getGoalNumber();
    }

    default NBTCompound getTeamData(Player p) {
        Team t = p.getScoreboard().getEntryTeam(p.getName());
        String teamName = t != null ? t.getName() : p.getUniqueId().toString();

        return getData().getOrCreateCompound("team_data").getOrCreateCompound(teamName);
    }
    
    default void setCompletion(Player p, int completion) {
        getTeamData(p).setInteger("completion", completion);
        
        if (isCompleted(p)) {
            subscribe(p, false);
        }

        getGame().update();
    }

    default void setCompleted(Player p) {
        setCompletion(p, getGoalNumber());
    }

    default void addCompletion(Player p, int delta) {
        int old = getCompletion(p);
        
        setCompletion(p, old + delta);
    }
    
    default void subscribe(Player p, boolean subscribe) {
        NBTList<UUID> subscribers = getData().getUUIDList("subscribers");

        if (subscribe && !subscribers.contains(p.getUniqueId())) {
            subscribers.add(p.getUniqueId());
        }
        else {
            subscribers.remove(p.getUniqueId());
        }

        getGame().updatePlayerSidebar(p);
    }

    default void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    default void unloadEvents() {
        HandlerList.unregisterAll(this);
    }
}
