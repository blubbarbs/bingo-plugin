package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Game;

import dev.jorel.commandapi.nbtapi.NBTCompound;

public abstract class Goal implements Listener {
    protected Game game;
    protected NBTCompound data;

    public Goal(Game game, NBTCompound data) {
        this.game = game;
        this.data = data;
    }

    public int getGoalNumber() {
        return data.getInteger("goal");
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

        return data.getOrCreateCompound("team_data").getOrCreateCompound(t.getName());
    }

    public int getCompletion(Player p) {
        NBTCompound data = getTeamData(p);

        if (!data.hasKey("completion")) {
            data.setInteger("completion", 0);
        }

        return data.getInteger("completion");
    }

    public boolean isCompleted(Player p) {
        return getCompletion(p) >= getGoalNumber();
    }

    public String getTooltip() {
        return this.getTitle() + "\n" + this.getDescription();
    }

    public abstract String getIcon();
    public abstract String getTitle();
    public abstract String getDescription();
    
    public void setGoalNumber(int number) {
        data.setInteger("goal", number);
    }

    public void setCompletion(Player p, int set) {
        getTeamData(p).setInteger("completion", set);
    }

    public void setCompleted(Player p) {
        setCompletion(p, getGoalNumber());
    }

    public void addCompletion(Player p, int delta) {
        int old = getCompletion(p);

        setCompletion(p, old + delta);
    }
}
