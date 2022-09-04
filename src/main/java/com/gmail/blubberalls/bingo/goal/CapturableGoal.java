package com.gmail.blubberalls.bingo.goal;

import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public abstract class CapturableGoal extends Goal {
    
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
            tooltip += "\n" + description;
        }

        if (!progressDescription.isEmpty()) {
            tooltip += "\n\n" + progressDescription;
        }
        
        if (!completionDescription.isEmpty()) {
            tooltip += "\n\n" + completionDescription;
        }

        return tooltip;
    }

    @Override
    public String getSidebarTitleFor(Team t) {
        Team completor = getWhoCompleted();

        if (completor != null) {
            if (t.equals(getWhoCompleted())) {
                return ChatColor.LIGHT_PURPLE + "" + getTitle() + ChatColor.RESET + " : " + t.getColor() + ChatColor.ITALIC + t.getDisplayName();
            }
            else {
                return ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH + getTitle() + ChatColor.RESET + " : " + completor.getColor() + ChatColor.ITALIC + completor.getDisplayName();
            }
        }
        else {
            return ChatColor.LIGHT_PURPLE + getTitle();         
        }
    }

    @Override
    public void onCompletorChange(Team oldCompletor) {
        Team currentCompletor = getWhoCompleted();
        ComponentBuilder completionMessage = new ComponentBuilder();
        TextComponent goalComponent = new TextComponent(difficulty.getColor() + "[" + getTitle() + ChatColor.GREEN + "]");

        if (oldCompletor == null) {
            completionMessage.append("Team " + currentCompletor.getColor() + currentCompletor.getDisplayName() + ChatColor.RESET + " has captured ", FormatRetention.NONE);
            completionMessage.append(goalComponent, FormatRetention.NONE);
            completionMessage.append("!", FormatRetention.NONE);
        }
        else if (currentCompletor == null) {
            completionMessage.append("Team " + oldCompletor.getColor() + oldCompletor.getDisplayName() + ChatColor.RESET + " has LOST ", FormatRetention.NONE);
            completionMessage.append(goalComponent, FormatRetention.NONE);
            completionMessage.append("!!", FormatRetention.NONE);
        }
        else {
            completionMessage.append("Team " + currentCompletor.getColor() + oldCompletor.getDisplayName() + ChatColor.RESET + " has stolen ");
            completionMessage.append(goalComponent, FormatRetention.NONE);
            completionMessage.append(" from ", FormatRetention.NONE);
            completionMessage.append(oldCompletor.getColor() + oldCompletor.getDisplayName() + ChatColor.RESET + "!!!");
        }

        goalComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(getDescription())));
        game.broadcastMessage(completionMessage.create());
        game.update();
    }
}
