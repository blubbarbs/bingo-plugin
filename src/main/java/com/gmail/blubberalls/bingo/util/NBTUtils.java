package com.gmail.blubberalls.bingo.util;

import java.util.ArrayList;
import java.util.List;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;

public class NBTUtils {
    public static void set(NBTCompound compound, String key, Object value) {
        if (value instanceof Byte) {
            compound.setByte(key, (byte) value);
        }
        else if (value instanceof Integer) {
            compound.setInteger(key, (int) value);
        }
        else if (value instanceof Short) {
            compound.setShort(key, (short) value);
        }
        else if (value instanceof Double) {
            compound.setDouble(key, (double) value);
        }
        else if (value instanceof Float) {
            compound.setFloat(key, (float) value);
        }
        else if (value instanceof Boolean) {
            compound.setBoolean(key, (boolean) value);
        }
        else if (value instanceof String) {
            compound.setString(key, (String) value);
        }
        else if (value instanceof NBTCompound) {
            if (compound.getCompound(key) != null) {
                clear(compound.getCompound(key));
            }

            compound.getOrCreateCompound(key).mergeCompound((NBTCompound) value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void set(NBTCompound compound, String key, List<T> values) {
        Object topValue = values.toArray()[0];

        if (topValue instanceof Byte) {
            byte[] bytes = new byte[values.size()];

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) values.get(i);
            }

            compound.setByteArray(key, bytes);
        }
        else if (topValue instanceof Integer) {
            List<Integer> integerList = (List<Integer>) values;

            compound.getIntegerList(key).addAll(integerList);
        }
        else if (topValue instanceof Double) {
            List<Double> doubleList = (List<Double>) values;
        
            compound.getDoubleList(key).addAll(doubleList);
        }
        else if (topValue instanceof Float) {
            List<Float> floatList = (List<Float>) values;
        
            compound.getFloatList(key).addAll(floatList);
        }
        else if (topValue instanceof String) {
            List<String> stringList = (List<String>) values;
        
            compound.getStringList(key).addAll(stringList);
        }
        else if (topValue instanceof NBTCompound) {
            NBTCompoundList l = compound.getCompoundList(key);
        
            for (NBTCompound c : (List<NBTCompound>) values) {
                l.addCompound(c);
            }
        }
    }

    public static void clear(NBTCompound compound) {
        ArrayList<String> keys = new ArrayList<String>();

        compound.getKeys().forEach(k -> keys.add(k));
        keys.forEach(k -> compound.removeKey(k));
    }
}
