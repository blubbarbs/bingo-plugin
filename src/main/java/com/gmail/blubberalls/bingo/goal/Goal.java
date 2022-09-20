package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.Game;
import com.gmail.blubberalls.bingo.goal.goal_data.GoalData;
import com.gmail.blubberalls.bingo.util.TextComponents;

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

    public abstract String getTitle();
    public abstract String getIconPath();
    public abstract String getDescription();

    public Game getGame() {
        return game;
    }
    
    public NBTCompound getSavedData() {
        return savedData;
    }

    public GoalDifficulty getDifficulty() {
        return difficulty;
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

    public String getCompletionDescription() {
        Team completor = getWhoCompleted();

        return isCompleted() ? ChatColor.ITALIC + "Completed By: " + completor.getColor() + "" + ChatColor.ITALIC + completor.getDisplayName() : "";
    }

    public String getProgressDescriptionFor(Team t) {
        return "";
    }

    public String getTooltipFor(Team t) {
        String tooltip = difficulty.getColor() + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + getTitle() + ChatColor.RESET;
        String description = getDescription();
        String progressDescription = getProgressDescriptionFor(t);
        String completionDescription = getCompletionDescription();
        
        if (!description.isEmpty()) {
            tooltip += "\n" + ChatColor.RESET + description;
        }

        if (!isCompleted() && !progressDescription.isEmpty()) {
            tooltip += "\n\n" + ChatColor.RESET + progressDescription;
        }
        else if (isCompleted() && !completionDescription.isEmpty()) {
            tooltip += "\n\n" + ChatColor.RESET + completionDescription;
        }

        return tooltip;
    }
    
    public BaseComponent getClickboxFor(Team t) {
        BaseComponent clickbox = TextComponents.icon("bingo.frames.frame_blank");

        clickbox.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getTooltipFor(t))));
        clickbox.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bingo togglesubscribe " + getName()));

        return clickbox;
    }

    public String getSidebarTitleFor(Team t) {        
        Team completor = getWhoCompleted();
        
        if (completor == null) {
            return difficulty.getColor() + "" + ChatColor.BOLD + getTitle();
        }
        else {
            return ChatColor.GRAY + "" + ChatColor.BOLD + ChatColor.STRIKETHROUGH + getTitle() + completor.getColor() + " (" + completor.getDisplayName() + ")";
        }
    }
    
    @Override
    public void setCompletedFor(Team t, boolean completed) {
        String oldCompletorName = getWhoCompletedName();
        
        GoalData.super.setCompletedFor(t, completed);

        if (oldCompletorName.equals(getWhoCompletedName())) return;

        onCompletorChange(game.getTeam(oldCompletorName));
    }

    public void onCompletorChange(Team oldCompletor) {
        Team currentCompletor = getWhoCompleted();

        if (currentCompletor == null) return; 

        TextComponent goalComponent = new TextComponent(difficulty.getColor() + "[" + getTitle() + "]");
        ComponentBuilder completionMessage = new ComponentBuilder();

        completionMessage.append("Team " + currentCompletor.getColor() + currentCompletor.getDisplayName() + ChatColor.RESET + " has completed ", FormatRetention.NONE);
        completionMessage.append(goalComponent);
        goalComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        game.broadcastMessage(completionMessage.create());
        game.update();
        unload();
    }

    public void load() {
        if (!isCompleted()) {
            Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
        }
    }

    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
