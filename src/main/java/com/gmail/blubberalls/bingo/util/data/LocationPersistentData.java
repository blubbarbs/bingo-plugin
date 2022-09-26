package com.gmail.blubberalls.bingo.util.data;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class LocationPersistentData implements PersistentDataType<byte[], Location> {

    public static LocationPersistentData INSTANCE = new LocationPersistentData();

    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Location fromPrimitive(byte[] primitive, PersistentDataAdapterContext ctx) {
        ByteBuffer bb = ByteBuffer.wrap(primitive);
        World world = Bukkit.getWorld(new UUID(bb.getLong(), bb.getLong()));
        double x = bb.getDouble();
        double y = bb.getDouble();
        double z = bb.getDouble();
        float yaw = bb.getFloat();
        float pitch = bb.getFloat();
        
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public byte[] toPrimitive(Location complex, PersistentDataAdapterContext ctx) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[48]);
        
        bb.putLong(complex.getWorld().getUID().getMostSignificantBits());
        bb.putLong(complex.getWorld().getUID().getLeastSignificantBits());
        bb.putDouble(complex.getX());
        bb.putDouble(complex.getY());
        bb.putDouble(complex.getZ());
        bb.putFloat(complex.getYaw());
        bb.putFloat(complex.getPitch());
        
        return bb.array();  
    }
}
