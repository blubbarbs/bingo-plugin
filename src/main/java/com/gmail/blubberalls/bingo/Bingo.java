package com.gmail.blubberalls.bingo;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gmail.blubberalls.custom_events.CustomEvents;

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

        Bukkit.getOnlinePlayers().forEach(p -> CustomEvents.registerInventoryListener(p.getOpenInventory()));
        GAME.loadGame();
        Bukkit.getPluginManager().registerEvents(this, this);
    }   

    @Override
    public void onDisable() {
        GAME.saveGame();
        GAME.unregisterGoalEvents();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        GAME.updatePlayerSidebar(event.getPlayer());
        CustomEvents.registerInventoryListener(event.getPlayer().getOpenInventory());
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        CustomEvents.registerInventoryListener(event.getView());
    }
}
