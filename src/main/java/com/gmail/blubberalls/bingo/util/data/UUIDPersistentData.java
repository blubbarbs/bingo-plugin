package com.gmail.blubberalls.bingo.util.data;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class UUIDPersistentData implements PersistentDataType<byte[], UUID> {
    
    public static UUIDPersistentData INSTANCE = new UUIDPersistentData();

    @Override
    public Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public UUID fromPrimitive(byte[] primitive, PersistentDataAdapterContext ctx) {
        ByteBuffer bb = ByteBuffer.wrap(primitive);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        
        return new UUID(firstLong, secondLong);
    }

    @Override
    public byte[] toPrimitive(UUID complex, PersistentDataAdapterContext ctx) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(complex.getMostSignificantBits());
        bb.putLong(complex.getLeastSignificantBits());
        
        return bb.array();  
    }   
}
