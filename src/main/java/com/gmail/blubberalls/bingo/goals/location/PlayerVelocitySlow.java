package com.gmail.blubberalls.bingo.goals.location;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;
import com.gmail.blubberalls.custom_events.event.PlayerExistEvent;

public class PlayerVelocitySlow extends Goal {

    @Override
    public String getTitle() {
        return "Slower than Slow";
    }

    @Override
    public String getIconPath() {
        return Icons.MISC("slow");
    }

    @Override
    public String getDescription() {
        return "Go extremely slowly. Crouch in a cobweb while shielding and walking in a Slime Block with a potion of slowness active.";
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerExist(PlayerExistEvent event) {        
        if (!event.getPlayer().isBlocking()
        ||  !event.getPlayer().isSneaking()
        ||  !event.getPlayer().isOnGround()
        ||  event.getPlayer().getLocation().getBlock().getType() != Material.COBWEB
        ||  event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.SLIME_BLOCK
        ||  !event.getPlayer().hasPotionEffect(PotionEffectType.SLOW)
        ||  event.getPlayer().getPotionEffect(PotionEffectType.SLOW).getAmplifier() < 2
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }
}
