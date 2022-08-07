package com.gmail.blubberalls.bingo.util;

public class TextUtils {
    public static String captailizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static String[] capitalizeFirstLetters(String[] words) {
        String[] capitalized = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            
            capitalized[i] = captailizeFirstLetter(word);
        }

        return capitalized;
    }

    public static String join(String[] words, String joiner) {
        String joined = words[0];

        for (int i = 1; i < words.length; i++) {
            String word = words[i];

            joined += joiner + word;
        }

        return joined;
    }
}
