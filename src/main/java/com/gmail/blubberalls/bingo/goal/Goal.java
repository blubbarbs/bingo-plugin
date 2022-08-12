package com.gmail.blubberalls.bingo.goal;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_types.DefaultGoal;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTList;

public abstract class Goal implements DefaultGoal {
    protected Game game;
    protected NBTCompound goalData;
    protected NBTCompound data;

    public Goal(Game game, NBTCompound goalData, NBTCompound instanceData) {
        this.game = game;
        this.goalData = goalData;
        this.data = instanceData;
    }

    public Game getGame() {
        return game;
    }

    public NBTCompound getGoalData() {
        return goalData;
    }

    public NBTCompound getData() {
        return data;
    }

    public String getName() {
        return goalData.getString("name");
    }

    public String getTitle() {
        return data.hasKey("title") ? data.getString("title") : goalData.getString("title");
    }

    public String getIcon() {
        return data.hasKey("icon") ? data.getString("icon") : goalData.getString("icon");
    }
    
    public String getDescription() {
        return data.hasKey("description") ? data.getString("description") : goalData.getString("description");
    }

    public String getTooltip() {
        return getTitle() + "\n" + getDescription();
    }

    public int getGoal() {
        return data.hasKey("goal") ? data.getInteger("goal") : 1;
    }

    public int getCompletion(Player p) {
        return getTeamData(p).getInteger("completion");
    }

    public boolean isSubscribed(Player p) {
        return !isCompleted(p) && true;
    }

    public boolean isCompleted(Player p) {
        return getCompletion(p) >= getGoal();
    }

    public String getTeamName(Player p) {
        Team t = p.getScoreboard().getEntryTeam(p.getName());
        return t != null ? t.getName() : p.getUniqueId().toString();
    }

    public NBTCompound getTeamData(Player p) {
        return data.getOrCreateCompound("team_data").getOrCreateCompound(getTeamName(p));
    }
    
    public void setCompletion(Player p, int completion) {
        getTeamData(p).setInteger("completion", completion);
        
        if (isCompleted(p)) {
            setSubscription(p, false);
            data.setString("completed_by", getTeamName(p));
        }

        game.update();
    }

    public void setCompleted(Player p) {
        setCompletion(p, getGoal());
    }

    public void addCompletion(Player p, int delta) {
        int old = getCompletion(p);
        
        setCompletion(p, old + delta);
    }
    
    public void setSubscription(Player p, boolean subscribe) {
        NBTList<UUID> subscribers = getData().getUUIDList("subscribers");

        if (subscribe && !subscribers.contains(p.getUniqueId())) {
            subscribers.add(p.getUniqueId());
        }
        else {
            subscribers.remove(p.getUniqueId());
        }
    }

    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    public void unloadEvents() {
        HandlerList.unregisterAll(this);
    }

}
