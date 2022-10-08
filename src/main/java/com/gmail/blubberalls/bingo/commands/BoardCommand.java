package com.gmail.blubberalls.bingo.commands;

import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;

@Command("board")
@Alias({"b"})
public class BoardCommand {
    
    @Default
    public static void sendBoard(Player p) {
        BingoCommand.sendBoard(p);
    }
}
