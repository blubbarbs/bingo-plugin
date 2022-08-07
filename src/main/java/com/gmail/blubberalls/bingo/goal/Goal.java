package com.gmail.blubberalls.bingo.goal;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;

public abstract class Goal implements Listener {
    protected Game game;
    protected NBTCompound data;

    public Goal(Game game, NBTCompound data) {
        this.game = game;
        this.data = data;
    }

    public abstract String getIcon();
    public abstract String getTitle();
    public abstract String getDescription();

    public int getGoalNumber() {
        return 1;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return data.getString("name");
    }

    public NBTCompound getData() {
        return data;
    }

    public NBTCompound getTeamData(Player p) {
        Team t = p.getScoreboard().getEntryTeam(p.getName());
        String teamName = t != null ? t.getName() : p.getUniqueId().toString();

        return data.getOrCreateCompound("team_data").getOrCreateCompound(teamName);
    }

    public boolean isSubscribed(Player p) {
        return !isCompleted(p) && true;
        //return data.getUUIDList("subscribers").contains(p.getUniqueId());
    }

    public String getCompletionStatus(Player p) {
        return isCompleted(p) ? "TRUE" : "FALSE";
    }

    public boolean isCompleted(Player p) {
        return getTeamData(p).getBoolean("completion") != null ? getTeamData(p).getBoolean("completion") : false;
    }

    public String getTooltip() {
        return this.getTitle() + "\n" + this.getDescription();
    }

    public void setCompleted(Player p) {
        getTeamData(p).setBoolean("completion", true);

        subscribe(p, false);
    }

    public void subscribe(Player p, boolean subscribe) {
        NBTList<UUID> subscribers = data.getUUIDList("subscribers");

        if (subscribe && !subscribers.contains(p.getUniqueId())) {
            subscribers.add(p.getUniqueId());
        }
        else {
            subscribers.remove(p.getUniqueId());
        }

        game.updatePlayerSidebar(p);
    }
}
