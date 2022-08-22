package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_data.GoalData;
import com.gmail.blubberalls.bingo.util.TextComponents;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public abstract class Goal implements Listener, GoalData {
    protected Game game;
    protected NBTCompound savedData;

    public Game getGame() {
        return game;
    }
    
    public GoalDifficulty getDifficulty() {
        return GoalDifficulty.EASY;
    }

    public NBTCompound getSavedData() {
        return savedData;
    }

    // Having this set to true prevents events being unloaded
    // after the goal is completed
    public boolean hasEventsWhenCompleted() {
        return false;
    }

    public String getTitle() {
        return TextUtils.capitalizeFirstLetters(getName(), "_", " ");
    }

    public String getIconPath() {
        return "bingo.icons.original_test";
    }
    
    public String getDescription() {
        return "Default description";
    }

    public String getTooltip() {
        return getTitle() + "\n" + getDescription();
    }

    public BaseComponent[] getTextIcon() {
        ComponentBuilder builder = new ComponentBuilder();
        TranslatableComponent background = isCompleted() ? TextComponents.icon("bingo.team_backgrounds." + getWhoCompleted().getName().toLowerCase()) : TextComponents.icon("bingo.team_backgrounds.none");
        TranslatableComponent icon = TextComponents.icon(getIconPath());
        TranslatableComponent border = TextComponents.icon("bingo.blank");

        builder.append(background);
        builder.append(TextComponents.ICON_OFFSET);
        builder.append(border);
        builder.append(TextComponents.ICON_OFFSET);
        builder.append(icon);
        background.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getTooltip())));
        background.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bingo togglesubscribe " + getName()));

        return builder.create();
    }

    public BaseComponent[] getCompletionMessage(Team t) {
        ComponentBuilder messageBuilder = new ComponentBuilder();
        TextComponent baseMessage = new TextComponent("Team " + t.getColor() + t.getDisplayName() + ChatColor.RESET + " has completed ");
        TextComponent goalMessage = new TextComponent(ChatColor.GREEN + "[" + getTitle() + ChatColor.GREEN + "]" + ChatColor.RESET + "!");

        messageBuilder.append(baseMessage);
        messageBuilder.append(goalMessage);
        goalMessage.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));

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
        if (isCompleted()) {
            if (t.equals(getWhoCompleted())) {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + " ✓";
            }
            else {
                return ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + "" + ChatColor.RED + ChatColor.BOLD + " ✕";
            }
        }
        else {
            return ChatColor.GOLD + getTitle();
        }
    }

    public boolean willChangeCompletor(Team t, boolean completed) {
        Team oldCompletor = getWhoCompleted();

        return (oldCompletor == null && completed)
            || (t.equals(oldCompletor) && isCompleted() != completed);
    }

    public void setTeamCompleted(Team t, boolean completed) {
        if (!willChangeCompletor(t, completed)) return;

        if (completed) {
            game.broadcastMessage(getCompletionMessage(t));
            game.broadcastSound(game.getTeamPlayers(t), Sound.ENTITY_PLAYER_LEVELUP);
            game.broadcastSound(game.getPlayersNotInTeam(t), Sound.BLOCK_CONDUIT_DEACTIVATE);
        }
        else {
            game.broadcastMessage(getUncompletionMessage(t));
            game.broadcastSound(game.getTeamPlayers(t), Sound.BLOCK_CONDUIT_DEACTIVATE);
            game.broadcastSound(game.getPlayersNotInTeam(t), Sound.BLOCK_CONDUIT_DEACTIVATE);
        }
        
        if (!hasEventsWhenCompleted() && completed) {
            unloadEvents();
        }

        GoalData.super.setTeamCompleted(t, completed);
        game.update();
    }

    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    public void unloadEvents() {
        HandlerList.unregisterAll(this);
    }
}
