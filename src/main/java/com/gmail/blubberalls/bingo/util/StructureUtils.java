package com.gmail.blubberalls.bingo.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.generator.strucutre.CraftStructure;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureUtils {

    public static boolean isLocInStructure(Location loc, org.bukkit.generator.structure.Structure structure) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPosition nmsLoc = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        Structure nmsStructure = CraftStructure.bukkitToMinecraft(structure);
        StructureManager nmsStructureManager = nmsWorld.a();
        //StructureStart nmsStartAtLoc = nmsStructureManager.a(nmsLoc, nmsStructure);
        StructureStart nmsStartContainingLoc = nmsStructureManager.b(nmsLoc, nmsStructure);
        //List<StructureStart> nmsChunkStarts = nmsStructureManager.a(SectionPosition.a(nmsLoc), nmsStructure);

        /*
        if (nmsChunkStarts.size() > 0) {
            Bukkit.getLogger().info("-----------------------------------------");
            Bukkit.getLogger().info("NUM STARTS FOR " + structure.getKey().getKey() + " - " + nmsChunkStarts.size());            
        }

        for (StructureStart nmsStart : nmsChunkStarts) {
            List<StructurePiece> nmsStartPieces = nmsStart.i();

            Bukkit.getLogger().info("NUM PIECES - " + nmsStartPieces.size());

            for (StructurePiece nmsStartPiece : nmsStart.i()) {
                Bukkit.getLogger().info(nmsStartPiece.f().toString());
            }
        }


        if (!nmsStartAtLoc.b() && !nmsStartContainingLoc.b()) {
            return false;
        }
        */

        return nmsStartContainingLoc.b();
    }


}
