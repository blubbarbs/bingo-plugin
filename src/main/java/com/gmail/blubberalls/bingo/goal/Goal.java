package com.gmail.blubberalls.bingo.goal;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTList;
import net.md_5.bungee.api.ChatColor;

public abstract class Goal implements Listener {
    Game game;
    NBTCompound savedData = new NBTContainer();

    public Game getGame() {
        return game;
    }

    public NBTCompound getSavedData() {
        return savedData;
    }

    public int getMinimumGoal() {
        return 1;
    }

    public int getMaximumGoal() {
        return 1;
    }

    public String getName() {
        return savedData.getString("name");
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

    public int getGoal() {
        return savedData.hasKey("goal") ? savedData.getInteger("goal") : 1;
    }

    public NBTCompound getTeamData(Player p) {
        return savedData.getOrCreateCompound("team_data").getOrCreateCompound(getTeamName(p));
    }

    public String getTeamName(Player p) {
        Team t = p.getScoreboard().getEntryTeam(p.getName());
        return t != null ? t.getName() : p.getUniqueId().toString();
    }

    public int getCompletion(Player p) {
        return getTeamData(p).getInteger("completion");
    }

    public String getCompletionStatus(Player p) {
        if (getGoal() == 1) {
            return ChatColor.GREEN + getTitle();
        }
        else {
            return ChatColor.GRAY + getTitle() + " " + ChatColor.GREEN + getCompletion(p) + "/" + getGoal();
        }
    }

    public boolean isCompleted(Player p) {
        return getCompletion(p) >= getGoal();
    }
    
    public boolean isSubscribed(Player p) {
        return !isCompleted(p) && true;
    }

    public void setCompletion(Player p, int completion) {
        getTeamData(p).setInteger("completion", completion);
        
        if (isCompleted(p)) {
            setSubscription(p, false);
            savedData.setString("completed_by", getTeamName(p));
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
        NBTList<UUID> subscribers = savedData.getUUIDList("subscribers");

        if (subscribe && !subscribers.contains(p.getUniqueId())) {
            subscribers.add(p.getUniqueId());
        }
        else {
            subscribers.remove(p.getUniqueId());
        }
    }

    public void reset() {
        getSavedData().setInteger("goal", game.getRandom().nextInt(getMinimumGoal(), getMaximumGoal() + 1));
    }

    public void loadNBT(NBTCompound savedData) {
        this.savedData.mergeCompound(savedData);
    }

    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    public void unloadEvents() {
        HandlerList.unregisterAll(this);
    }

}
