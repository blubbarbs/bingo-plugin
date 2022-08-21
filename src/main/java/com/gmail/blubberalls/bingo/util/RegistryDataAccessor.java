package com.gmail.blubberalls.bingo.util;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;

public class RegistryDataAccessor {
    public static <T extends Keyed> T convertKey(NamespacedKey key, Registry<T> registry) {
        return registry.get(key);
    }
    
    public static <T extends Keyed> T convertKeyString(String key, Registry<T> registry) {
        return convertKey(NamespacedKey.fromString(key), registry);
    }

    public static <T extends Keyed> Collection<T> convertKeys(Collection<NamespacedKey> keys, Registry<T> registry) {
        ArrayList<T> converted = new ArrayList<T>();

        for (NamespacedKey key : keys) {
            converted.add(convertKey(key, registry));
        }

        return converted;
    }

    public static <T extends Keyed> Collection<T> convertKeyStrings(Collection<String> keys, Registry<T> registry) {
        ArrayList<T> converted = new ArrayList<T>();

        for (String key : keys) {
            converted.add(convertKeyString(key, registry));
        }

        return converted;
    }
}
