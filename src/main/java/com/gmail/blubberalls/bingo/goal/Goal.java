package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_data.GoalData;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import net.md_5.bungee.api.ChatColor;

public abstract class Goal implements Listener, GoalData {
    Game game;
    NBTCompound savedData = new NBTContainer();

    public Game getGame() {
        return game;
    }

    public NBTCompound getSavedData() {
        return savedData;
    }

    public String getTitle() {
        return TextUtils.capitalizeFirstLetters(getName(), "_", " ");
    }

    public String getIcon() {
        return "bingo.icons.test";
    }
    
    public String getDescription() {
        return "Default description";
    }

    public String getTooltip() {
        return getTitle() + "\n" + getDescription();
    }

    public String getTeamCompletionStatus(Player p) {
        return ChatColor.GREEN + getTitle();         
    }

    public void setTeamCompletion(Player p, int completion) {
        GoalData.super.setTeamCompletion(p, completion);

        onCompletionUpdate();
    }

    public void onCompletionUpdate() {
        if (isCompleted()) {
            unloadEvents();
        }
        
        game.update();
    }

    public void initializeNewGoal() {}

    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    public void unloadEvents() {
        HandlerList.unregisterAll(this);
    }
}
