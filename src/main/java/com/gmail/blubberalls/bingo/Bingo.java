package com.gmail.blubberalls.bingo;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gmail.blubberalls.custom_events.CustomExistListener;
import com.gmail.blubberalls.custom_events.CustomInventoryListener;

import dev.jorel.commandapi.CommandAPI;

public class Bingo extends JavaPlugin implements Listener {
    private static Bingo INSTANCE;
    private static Game GAME;
    private static ProtocolManager PROTOCOL_MANAGER;

    public static Bingo getInstance() {
        return INSTANCE;
    }

    public static Game getGame() {
        return GAME;
    }

    public static ProtocolManager getProtocolManager() {
        return PROTOCOL_MANAGER;
    }

    @Override
    public void onLoad() {
        CommandAPI.registerCommand(BingoCommands.class);
    }

    @Override
    public void onEnable() {        
        INSTANCE = this;
        GAME = new Game();
        PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new CustomExistListener(), this);
        GAME.loadGame();
    }   

    @Override
    public void onDisable() {
        GAME.saveGame();
        GAME.unregisterGoalEvents();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        if (!GAME.isPlaying(event.getPlayer())) return;

        event.getPlayer().setScoreboard(GAME.getScoreboard());
        GAME.updatePlayerSidebar(event.getPlayer());
    }
}
