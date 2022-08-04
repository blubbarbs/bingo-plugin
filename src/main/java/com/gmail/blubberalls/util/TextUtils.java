package com.gmail.blubberalls.util;

public class TextUtils {
    public static String captailizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
