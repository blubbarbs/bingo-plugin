package com.gmail.blubberalls.bingo.util;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.generator.strucutre.CraftStructure;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureUtils {

    public static boolean isLocInStructure(Location loc, org.bukkit.generator.structure.Structure structure) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPosition nmsLoc = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        Structure nmsStructure = CraftStructure.bukkitToMinecraft(structure);
        StructureManager nmsStructureManager = nmsWorld.a();
        StructureStart nmsStartContainingLoc = nmsStructureManager.b(nmsLoc, nmsStructure);

        return nmsStartContainingLoc.b();
    }


}
