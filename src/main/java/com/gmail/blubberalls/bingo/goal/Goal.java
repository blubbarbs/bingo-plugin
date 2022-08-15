package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_data.GoalData;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public abstract class Goal implements Listener, GoalData {
    Game game;
    NBTCompound savedData = new NBTContainer();

    public Game getGame() {
        return game;
    }

    public NBTCompound getSavedData() {
        return savedData;
    }

    public boolean shouldRegisterEvents() {
        return !isCompleted();
    }

    public boolean shouldUnregisterEvents() {
        return true;
    }

    public String getTitle() {
        return TextUtils.capitalizeFirstLetters(getName(), "_", " ");
    }

    public String getIconPath() {
        return "bingo.icons.test";
    }
    
    public String getDescription() {
        return "Default description";
    }

    public String getTooltip() {
        return getTitle() + "\n" + getDescription();
    }

    public Team getTeam(Player p) {
        return getGame().getTeam(p);
    }

    public BaseComponent[] getTextIcon() {
        ComponentBuilder builder = new ComponentBuilder();
        TranslatableComponent icon = TextUtils.getBoardComponent(0, getIconPath());
    
        icon.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getTooltip())));
        builder.append(icon);
        builder.append(TextUtils.getBoardComponent(-TextUtils.ICON_SIZE_PX - 1, "bingo.blank"));
        builder.append(TextUtils.getBoardComponent(-TextUtils.ICON_SIZE_PX - 1, "bingo.overlay.o"));

        return builder.create();
    }

    public BaseComponent[] getCompletionMessage(Team t) {
        ComponentBuilder messageBuilder = new ComponentBuilder();
        TextComponent baseMessage = new TextComponent("Team " + t.getColor() + t.getDisplayName() + ChatColor.RESET + " has completed ");
        TextComponent goalMessage = new TextComponent(ChatColor.GREEN + "[" + getTitle() + ChatColor.GREEN + "]" + ChatColor.RESET + "!");

        goalMessage.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        messageBuilder.append(baseMessage);
        messageBuilder.append(goalMessage);

        return messageBuilder.create();
    }

    public BaseComponent[] getUncompletionMessage(Team t) {
        ComponentBuilder messageBuilder = new ComponentBuilder();
        TextComponent baseMessage = new TextComponent("Team " + t.getColor() + t.getDisplayName() + ChatColor.RESET + " has lost ");
        TextComponent goalMessage = new TextComponent(ChatColor.GREEN + "[" + getTitle() + ChatColor.GREEN + "]" + ChatColor.RESET + "!");

        goalMessage.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        messageBuilder.append(baseMessage);
        messageBuilder.append(goalMessage);
        
        return messageBuilder.create();
    }

    public String getTeamCompletionStatus(Team t) {
        return ChatColor.GOLD + getTitle();         
    }

    public void setTeamCompletion(Team t, int completion) {
        Team oldCompletor = getWhoCompleted();
        
        if (oldCompletor == null && completion >= getGoal()) {
            game.broadcastMessage(getCompletionMessage(t));
            
            if (shouldUnregisterEvents()) {
                unloadEvents();
            }
        }
        else if (oldCompletor == t && completion < getGoal()) {
            game.broadcastMessage(getUncompletionMessage(t));
        }

        GoalData.super.setTeamCompletion(t, completion);
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
