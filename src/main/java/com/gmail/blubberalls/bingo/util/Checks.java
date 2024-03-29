package com.gmail.blubberalls.bingo.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.generator.strucutre.CraftStructure;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class Checks {    
    public static boolean isLocInStructure(Location loc, org.bukkit.generator.structure.Structure structure) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPosition nmsLoc = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        Structure nmsStructure = CraftStructure.bukkitToMinecraft(structure);
        StructureManager nmsStructureManager = nmsWorld.a();
        StructureStart nmsStartContainingLoc = nmsStructureManager.b(nmsLoc, nmsStructure);

        // Checks all children structures and sees if their BB intersects with the location
        return nmsStartContainingLoc.b();
    }

    public static org.bukkit.generator.structure.Structure getStructureAtLoc(Location loc) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPosition nmsLoc = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        StructureManager nmsStructureManager = nmsWorld.a();
        List<StructureStart> allStartsAtChunkPos = nmsStructureManager.a(new ChunkCoordIntPair(nmsLoc), (nmsStructure) -> true);

        for (StructureStart nmsStructureStart : allStartsAtChunkPos) {
            // If location is in structure start
            if (nmsStructureManager.a(nmsLoc, nmsStructureStart)) {
                Structure nmsStructure = nmsStructureStart.h();

                // nmsWorld.getMinecraftServer().getDimension() which is an ICustomRegistry
                return CraftStructure.minecraftToBukkit(nmsStructure, nmsWorld.n().aX());
            }
        }

        return null;
    }

    @SuppressWarnings("null")
    public static boolean areItemStacksEqual(ItemStack a, ItemStack b) {
        if (a == b) return true;
        else if (a == null && b != null) return false;
        else if (b == null && a != null) return false;
        else return a.equals(b);
    }

    public static boolean willPlaceInInventory(InventoryAction action) {
        switch(action) {
            case PLACE_ALL:
            case PLACE_SOME:
            case PLACE_ONE:
            case SWAP_WITH_CURSOR:
                return true;
            default:
                return false;
        }
    }

    public static boolean willTakeFromInventory(InventoryAction action) {
        switch(action) {
            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_SOME:
            case PICKUP_ONE:
            case MOVE_TO_OTHER_INVENTORY:
                return true;
            default:
                return false;
        }
    }
}
