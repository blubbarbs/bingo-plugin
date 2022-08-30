package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public abstract class Goal implements Listener, GoalData {
    protected Game game;
    protected GoalDifficulty difficulty;
    protected NBTCompound savedData;

    public Game getGame() {
        return game;
    }
    
    public NBTCompound getSavedData() {
        return savedData;
    }

    public String getTitle() {
        return TextUtils.capitalizeFirstLetters(getName(), "_", " ");
    }

    public GoalDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isCapturable() {
        return difficulty.equals(GoalDifficulty.CAPTURABLE);
    }

    public String getIconPath() {
        return "bingo.icons.original_test";
    }
    
    public String getDescription() {
        return "Default description";
    }

    public BaseComponent[] getIcon() {
        ComponentBuilder builder = new ComponentBuilder();
        TranslatableComponent frame = difficulty.getFrameFor(getWhoCompleted());
        TranslatableComponent icon = TextComponents.icon(getIconPath());

        builder.append(frame, FormatRetention.NONE);
        builder.append(TextComponents.FRAME_OFFSET, FormatRetention.NONE);
        builder.append(icon, FormatRetention.NONE);

        return builder.create();
    }

    public String getTooltipFor(Team t) {
        String tooltip = ChatColor.BOLD + "" + ChatColor.UNDERLINE + getTitle() +  ChatColor.RESET + "\n";
        tooltip += "Difficulty: " + difficulty.getColor() + "" + difficulty.name() +  ChatColor.RESET + "\n\n";
        tooltip += getDescription() +  ChatColor.RESET;

        if (isCapturable() || !isCompleted()) {
            tooltip += !getCompletionDescriptionFor(t).isEmpty() ? "\n\n" + getCompletionDescriptionFor(t) : "";
        }
        else {
            Team completor = getWhoCompleted();
            tooltip += ChatColor.ITALIC + "\n\nCompleted By: " + completor.getColor() + "" + ChatColor.ITALIC + completor.getDisplayName();
        }
        
        return tooltip;
    }
    
    public BaseComponent getClickboxFor(Team t) {
        BaseComponent clickbox = TextComponents.icon("bingo.frames.frame_blank");

        clickbox.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getTooltipFor(t))));
        clickbox.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bingo togglesubscribe " + getName()));

        return clickbox;
    }

    public String getCompletionDescriptionFor(Team t) {
        return "";
    }

    public BaseComponent[] getCompletionMessageFor(Team t) {
        ComponentBuilder messageBuilder = new ComponentBuilder();
        TextComponent baseMessage = new TextComponent("Team " + t.getColor() + t.getDisplayName() + ChatColor.RESET + " has completed ");
        TextComponent goalMessage = new TextComponent(ChatColor.GREEN + "[" + getTitle() + ChatColor.GREEN + "]" + ChatColor.RESET + "!");

        messageBuilder.append(baseMessage, FormatRetention.NONE);
        messageBuilder.append(goalMessage, FormatRetention.NONE);
        goalMessage.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));

        return messageBuilder.create();
    }

    public BaseComponent[] getUncompletionMessageFor(Team t) {
        ComponentBuilder messageBuilder = new ComponentBuilder();
        TextComponent baseMessage = new TextComponent("Team " + t.getColor() + t.getDisplayName() + ChatColor.RESET + " has lost ");
        TextComponent goalMessage = new TextComponent(ChatColor.GREEN + "[" + getTitle() + ChatColor.GREEN + "]" + ChatColor.RESET + "!");

        goalMessage.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        messageBuilder.append(baseMessage);
        messageBuilder.append(goalMessage);
        
        return messageBuilder.create();
    }

    public String getCompletionStatusFor(Team t) {        
        Team completor = getWhoCompleted();
        
        if (completor != null) {
            if (t.equals(completor)) {
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

    public boolean willChangeCompletorFor(Team t, boolean completed) {
        Team oldCompletor = getWhoCompleted();

        return (oldCompletor == null && completed)
            || (t.equals(oldCompletor) && isCompleted() != completed);
    }

    public void setCompletedFor(Team t, boolean completed) {
        if (!willChangeCompletorFor(t, completed)) return;

        if (completed) {
            game.broadcastMessage(getCompletionMessageFor(t));
            game.broadcastSound(game.getTeamPlayers(t), Sound.ENTITY_PLAYER_LEVELUP);
            game.broadcastSound(game.getPlayersNotInTeam(t), Sound.BLOCK_CONDUIT_DEACTIVATE);
        }
        else {
            game.broadcastMessage(getUncompletionMessageFor(t));
            game.broadcastSound(game.getTeamPlayers(t), Sound.BLOCK_CONDUIT_DEACTIVATE);
            game.broadcastSound(game.getPlayersNotInTeam(t), Sound.ENTITY_ENDERMAN_TELEPORT);
        }
        
        if (!isCapturable() && completed) {
            unloadEvents();
        }

        GoalData.super.setCompletedFor(t, completed);
        game.update();
    }
    
    public void setCompletedFor(Team t) {
        setCompletedFor(t, true);
    }

    public void setCompletedFor(Player p) {
        setCompletedFor(game.getTeam(p));
    }

    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

    public void unloadEvents() {
        HandlerList.unregisterAll(this);
    }
}
