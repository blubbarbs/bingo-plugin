package com.gmail.blubberalls.bingo.goals.attack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.blubberalls.bingo.goal.ScoredGoal;
import com.gmail.blubberalls.bingo.util.Icons;

public class DestroyDiamonds extends ScoredGoal {
    
    @Override
    public String getTitle() {
        return "Blood Diamonds";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("diamond");
    }

    @Override
    public String getDescription() {
        return "Throw 8 diamonds into lava. :)";
    }

    @Override
    public int getGoal() {
        return 8;
    }
    
    @EventHandler
    public void onItemDespawn(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.DROPPED_ITEM
        ||  event.getCause() != DamageCause.LAVA
        ||  event.getEntity().isDead()) return;

        Item i = (Item) event.getEntity();
        Player p = Bukkit.getPlayer(i.getThrower());

        if (i.getItemStack().getType() != Material.DIAMOND
        ||  !game.isPlayerPlaying(p)) return;

        i.remove();
        addCompletionFor(p, i.getItemStack().getAmount());
    }
}
