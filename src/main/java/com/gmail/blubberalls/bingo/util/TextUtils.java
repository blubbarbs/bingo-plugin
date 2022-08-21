package com.gmail.blubberalls.bingo.util;

import java.util.Arrays;

public class TextUtils {    
    public static String join(String[] words, String joiner) {
        String joined = words[0];

        for (int i = 1; i < words.length; i++) {
            String word = words[i];

            joined += joiner + word;
        }

        return joined;
    }

    public static String captailizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static String capitalizeFirstLetters(String words, String splitter, String joiner) {
        String[] split = words.split(splitter);

        for (int i = 0; i < split.length; i++) {
            split[i] = captailizeFirstLetter(split[i]);
        }

        return join(split, joiner);
    }

    public static String capitalizeFirstLetters(String words, String splitter) {
        return capitalizeFirstLetters(words, splitter, splitter);
    }

    public static String capitalizeFirstLetters(String words) {
        return capitalizeFirstLetters(words, " ", " ");
    }

    public static String getGrammaticalList(String[] words) {
        if (words.length == 1) {
            return words[0];
        }
        else if (words.length == 2) {
            return words[0] + " and " + words[1];
        }
        else {
            String last = words[words.length - 1];
            String[] beforeLast = Arrays.copyOfRange(words, 0, words.length - 1);

            return TextUtils.join(beforeLast, ",") + ", and " + last;
        }
    }
}
