package com.gmail.blubberalls.bingo;

import java.rmi.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.util.CustomSidebar;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Subcommand;

@Command("bingo")
public class BingoCommands {
    
    @Default
    public static void help(CommandSender sender) {
        sender.sendMessage("Testing");
    }
    
    @Subcommand("newgame") 
    public static void newGame(CommandSender sender) {
        Bukkit.getLogger().info("What the hell is going on, newgame");

        Bingo.getGame().newGame(10, 10);
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

}
