package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class EatHoneyBottle extends Goal {
    
    @Override
    public String getTitle() {
        return "Rumble in my Tummy";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("honey_bottle");
    }

    @Override
    public String getDescription() {
        return "Consume a Honey Bottle while poisoned.";
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.HONEY_BOTTLE
        ||  !event.getPlayer().hasPotionEffect(PotionEffectType.POISON)
        ||  !game.isPlayerPlaying(event.getPlayer())) return;
        
        setCompletedFor(event.getPlayer());
    }

}
