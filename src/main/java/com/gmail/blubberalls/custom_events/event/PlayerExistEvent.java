package com.gmail.blubberalls.custom_events.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerExistEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public PlayerExistEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
