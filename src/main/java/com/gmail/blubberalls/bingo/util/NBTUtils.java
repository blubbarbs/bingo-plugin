package com.gmail.blubberalls.bingo.util;

import java.util.ArrayList;

import de.tr7zw.nbtapi.NBTCompound;

public class NBTUtils {
    public static void clearNBT(NBTCompound compound) {
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(compound.getKeys());
        keys.forEach(key -> compound.removeKey(key));
    }
}
