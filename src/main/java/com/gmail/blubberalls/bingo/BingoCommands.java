package com.gmail.blubberalls.bingo;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.CustomSidebar;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AMultiLiteralArgument;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;

@Command("bingo")
public class BingoCommands {
    
    @Default
    public static void help(CommandSender sender) {
        sender.sendMessage("Testing");
    }
    
    @Subcommand("board")
    public static void sendBoard(Player p) {
        if (!Bingo.getGame().isPlayerPlaying(p)) {
            p.sendMessage(ChatColor.RED + "You are not currently playing in any game!");
            return;
        } 

        p.spigot().sendMessage(Bingo.getGame().getBoard(p));
    }

    @Subcommand("leaderboard")
    public static void sendLeaderboard(Player p) {
        if (!Bingo.getGame().isPlayerPlaying(p)) {
            p.sendMessage(ChatColor.RED + "You are not currently playing in any game!");
            return;
        } 

        Leaderboard<Team, Integer> leaderboard = Bingo.getGame().getLeaderboard();
        String message = ChatColor.UNDERLINE + "Leaderboard\n";

        for (Team t : Bingo.getGame().getTeams()) {
            message += t.getColor() + t.getDisplayName() + ChatColor.RESET + ": " + ChatColor.AQUA + leaderboard.get(t);
            message += "\n";
        }

        p.sendMessage(message.trim());
    }

    @Subcommand("newgame") 
    public static void newGame(CommandSender sender) {
        Bingo.getGame().newGame(5, 5);
        
        sender.sendMessage("New game started.");
    }

    @Subcommand("savegame")
    public static void saveGame(CommandSender sender) {
        Bingo.getGame().saveGame();

        sender.sendMessage("Game saved.");
    }

    @Subcommand("loadgame")
    public static void loadGame(CommandSender sender) {
        Bingo.getGame().loadGame();

        sender.sendMessage("Game loaded.");
    }

    @Subcommand("endgame")
    public static void endGame(CommandSender sender) {
        Bingo.getGame().endGame();

        sender.sendMessage("Game ended.");
    }

    @Subcommand("sendsidebar")
    public static void sendSidebar(Player p) {
        CustomSidebar.setPlayerSidebar(p, "WHEN SEPTEMBER ENDS", "A", "B", "C");
        p.sendMessage("Sent test sidebar.");
    }

    @Subcommand("resetsidebar")
    public static void resetSidebar(Player p) {
        CustomSidebar.resetPlayerSidebar(p);
        p.sendMessage("Reset sidebar.");
    }

    @Subcommand("join")
    public static void joinGame(Player p, @AMultiLiteralArgument({
        "aqua",
        "black",
        "blue",
        "dark_aqua",
        "dark_blue",
        "dark_gray",
        "dark_green",
        "dark_purple",
        "dark_red",
        "gold",
        "gray",
        "green",
        "light_purple",
        "red",
        "yellow",
        "white"
    }) String color) {
        Team t = Bingo.getGame().getTeam(ChatColor.valueOf(color.toUpperCase()));

        Bingo.getGame().addPlayer(p, t);
        p.sendMessage("Successfully joined the game on the " + t.getColor() + t.getDisplayName() + " team.");
    }

    @Subcommand("togglesubscribe")
    public static void toggleSubscribe(Player p, @AStringArgument String goalName) {
        if (!Bingo.getGame().isPlayerPlaying(p)) {
            p.sendMessage(ChatColor.RED + "You are not currently in any game!");
            return;
        }
        else if (Bingo.getGame().getGoal(goalName) == null) {
            p.sendMessage(ChatColor.RED + "That is not the name of a goal.");
            return;
        }
        Collection<Goal> subscribedGoals = Bingo.getGame().getPlayerSubscribedGoals(p);
        Goal goal = Bingo.getGame().getGoal(goalName);
        boolean isPlayerSubscribed = Bingo.getGame().isPlayerSubscribed(p, goal);

        if (!isPlayerSubscribed && subscribedGoals.size() >= 5) {
            p.sendMessage(ChatColor.RED + "You cannot subscribe to more than 5 goals at a time.");
            return;
        }

        Bingo.getGame().setPlayerGoalSubscription(p, goal, !isPlayerSubscribed);
    }
}
