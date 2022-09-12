package com.gmail.blubberalls.bingo.goal;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public abstract class CapturableGoal extends Goal {

    public abstract boolean teamCheck(Team t);

    public boolean willTeamComplete(Team t) {
        Team currentCompletor = getWhoCompleted();

        return (currentCompletor == null || currentCompletor.equals(t) || !teamCheck(currentCompletor)) && teamCheck(t);    
    }

    @Override
    public String getCompletionDescription() {
        Team completor = getWhoCompleted();

        return completor != null ? ChatColor.ITALIC  + "Current Holder: " + completor.getColor() + ChatColor.ITALIC + completor.getDisplayName() : "";
    }

    public String getTooltipFor(Team t) {
        String tooltip = difficulty.getColor() + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + getTitle() +  ChatColor.RESET;
        String description = getDescription();
        String progressDescription = getProgressDescriptionFor(t);
        String completionDescription = getCompletionDescription();
        
        if (!description.isEmpty()) {
            tooltip += "\n" + ChatColor.RESET + description;
        }

        if (!progressDescription.isEmpty()) {
            tooltip += "\n\n" + ChatColor.RESET + progressDescription;
        }
        
        if (!completionDescription.isEmpty()) {
            tooltip += "\n\n" + ChatColor.RESET + completionDescription;
        }

        return tooltip;
    }

    @Override
    public String getSidebarTitleFor(Team t) {        
        Team completor = getWhoCompleted();
        String sidebar = difficulty.getColor() + "" + ChatColor.BOLD + getTitle();

        if (completor != null) {
            sidebar += ChatColor.RESET + "\n" + "> Current Holder: " + completor.getColor() + completor.getDisplayName();
        }
        
        return sidebar;
    }

    @Override
    public void onCompletorChange(Team oldCompletor) {
        Team currentCompletor = getWhoCompleted();
        ComponentBuilder completionMessage = new ComponentBuilder();
        TextComponent goalComponent = new TextComponent(difficulty.getColor() + "[" + getTitle() + "]");

        if (oldCompletor == null) {
            completionMessage.append("Team " + currentCompletor.getColor() + currentCompletor.getDisplayName() + ChatColor.RESET + " has captured ", FormatRetention.NONE);
            completionMessage.append(goalComponent, FormatRetention.NONE);
        }
        else if (currentCompletor == null) {
            completionMessage.append("Team " + oldCompletor.getColor() + oldCompletor.getDisplayName() + ChatColor.RESET + " has LOST ", FormatRetention.NONE);
            completionMessage.append(goalComponent, FormatRetention.NONE);
        }
        else {
            completionMessage.append("Team " + currentCompletor.getColor() + currentCompletor.getDisplayName() + ChatColor.RESET + " has stolen ");
            completionMessage.append(goalComponent, FormatRetention.NONE);
            completionMessage.append(" from ", FormatRetention.NONE);
            completionMessage.append(oldCompletor.getColor() + oldCompletor.getDisplayName());
        }

        goalComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        game.broadcastMessage(completionMessage.create());
        game.update();
    }

    @Override
    public void load() {
        Bukkit.getPluginManager().registerEvents(this, Bingo.getInstance());
    }

}
