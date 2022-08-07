package com.gmail.blubberalls.bingo.goal;

import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.Game;

import de.tr7zw.nbtapi.NBTCompound;

public class NumerableGoal extends Goal {

    public NumerableGoal(Game game, NBTCompound data) {
        super(game, data);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getGoalNumber() {
        return data.getInteger("goal");
    }

    @Override
    public String getCompletionStatus(Player p) {
        return getCompletion(p) + "/" + getGoalNumber();
    }

    @Override
    public boolean isCompleted(Player p) {
        return getCompletion(p) >= getGoalNumber();
    }

    public int getRemaining(Player p) {
        return getGoalNumber() - getCompletion(p);
    }

    public int getCompletion(Player p) {
        NBTCompound data = getTeamData(p);

        return data.hasKey("completion") ? data.getInteger("completion") : 0;
    }

    public void setCompletion(Player p, int set) {
        getTeamData(p).setInteger("completion", set);
        
        if (isCompleted(p)) {
            data.getUUIDList("subscribers").remove(p.getUniqueId());
        }

        game.updatePlayerSidebar(p);
    }

    public void addCompletion(Player p, int delta) {
        int old = getCompletion(p);

        setCompletion(p, old + delta);
    }

    public void setGoalNumber(int number) {
        data.setInteger("goal", number);
    }
}