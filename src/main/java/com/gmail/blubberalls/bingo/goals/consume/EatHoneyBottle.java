package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

import com.gmail.blubberalls.bingo.goal.Goal;

public class EatHoneyBottle extends Goal {
    
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getItem().getType() != Material.HONEY_BOTTLE
        ||  !event.getPlayer().hasPotionEffect(PotionEffectType.POISON)) return;
        
        setCompletedFor(event.getPlayer());
    }

}
