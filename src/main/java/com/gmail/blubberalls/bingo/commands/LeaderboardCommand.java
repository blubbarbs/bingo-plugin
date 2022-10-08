package com.gmail.blubberalls.bingo.commands;

import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.BingoCommands;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;

@Command("leaderboard")
@Alias({"lb"})
public class LeaderboardCommand {
    @Default
    public static void sendLeaderboard(Player p) {
        BingoCommands.sendLeaderboard(p);
    }
}
