package com.gmail.blubberalls.bingo.goals.build;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.util.Icons;

public class BuildIdiotBox extends Goal {

    @Override
    public String getTitle() {
        return "Boxed In";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("iron_door");
    }

    @Override
    public String getDescription() {
        return "Trap yourself by stepping on a pressure plate surrounded by 4 iron doors with a solid block ceiling.";
    }

    public boolean isPressurePlate(Material m) {
        return m.name().endsWith("PRESSURE_PLATE");
    }

    public boolean hasCreatedIdiotBox(Block pressurePlate) {
        if (!pressurePlate.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().isSolid()) return false;
        
        Block north = pressurePlate.getRelative(BlockFace.NORTH);
        Block south = pressurePlate.getRelative(BlockFace.SOUTH);
        Block east = pressurePlate.getRelative(BlockFace.EAST);
        Block west = pressurePlate.getRelative(BlockFace.WEST);

        if (north.getType() != Material.IRON_DOOR
        ||  south.getType() != Material.IRON_DOOR
        ||  east.getType() != Material.IRON_DOOR
        ||  west.getType() != Material.IRON_DOOR) return false;

        Door northData = (Door) north.getBlockData();
        Door southData = (Door) south.getBlockData();
        Door eastData = (Door) east.getBlockData();
        Door westData = (Door) west.getBlockData();

        boolean northIsCorrect = northData.getHinge() == Hinge.LEFT ? northData.getFacing() == BlockFace.WEST : northData.getFacing() == BlockFace.EAST;
        boolean southIsCorrect = southData.getHinge() == Hinge.LEFT ? southData.getFacing() == BlockFace.EAST : southData.getFacing() == BlockFace.WEST;
        boolean eastIsCorrect = eastData.getHinge() == Hinge.LEFT ? eastData.getFacing() == BlockFace.NORTH : eastData.getFacing() == BlockFace.SOUTH;
        boolean westIsCorrect = westData.getHinge() == Hinge.LEFT ? westData.getFacing() == BlockFace.SOUTH : westData.getFacing() == BlockFace.NORTH;

        return northIsCorrect && southIsCorrect && eastIsCorrect && westIsCorrect;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {        
        if (event.getAction() != Action.PHYSICAL
        ||  !isPressurePlate(event.getClickedBlock().getType())
        ||  !hasCreatedIdiotBox(event.getClickedBlock())
        ||  !game.isPlayerPlaying(event.getPlayer())) return;

        setCompletedFor(event.getPlayer());
    }

}
