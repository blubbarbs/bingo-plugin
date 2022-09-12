package com.gmail.blubberalls.bingo.goals.consume;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.Bingo;
import com.gmail.blubberalls.bingo.goal.ScoredGoal;

import net.md_5.bungee.api.ChatColor;

public class EatRottenFlesh extends ScoredGoal {
    HashMap<Player, BukkitRunnable> scoreTasks = new HashMap<Player, BukkitRunnable>();
    
    @Override
    public int getGoal() {
        return 3;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        return "Current Streak: " + ChatColor.AQUA + getCompletionFor(t) + "/" + getGoal();
    }

    @Override
    public String getSidebarTitleFor(Team t) {
        return super.getSidebarTitleFor(t).replace("Completed", "Current Streak");
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (!game.isPlayerPlaying(event.getPlayer())
        ||  event.getItem().getType() != Material.ROTTEN_FLESH) return;
        
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {                
                addCompletionFor(event.getPlayer(), 1);
            }  
        };

        scoreTasks.put(event.getPlayer(), runnable);
        runnable.runTask(Bingo.getInstance());
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getNewEffect() == null
        ||  !event.getNewEffect().getType().equals(PotionEffectType.HUNGER)
        ||  event.getEntityType() != EntityType.PLAYER
        ||  event.getCause() != Cause.FOOD) return;

        Player p = (Player) event.getEntity();

        if (!scoreTasks.containsKey(p)) return;

        scoreTasks.get(p).cancel();
        scoreTasks.remove(p);
        setCompletionFor(p, 0);
    }

    @Override
    public void unload() {
        super.unload();
        scoreTasks.values().forEach(runnable -> runnable.cancel());
        scoreTasks.clear();
    }
}
