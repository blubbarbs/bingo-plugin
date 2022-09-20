package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class SplashWitch extends Goal {

    @Override
    public String getTitle() {
        return "Bruja Hada";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("splash_potion");
    }

    @Override
    public String getDescription() {
        return "Hit a Witch with a Splash Potion of Harming.";
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (!event.getAffectedEntities().stream().anyMatch(entity -> entity.getType() == EntityType.WITCH)
        ||  !event.getPotion().getEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.HARM)
        ||  !(event.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) event.getEntity().getShooter();

        setCompletedFor(p);
    }
}
