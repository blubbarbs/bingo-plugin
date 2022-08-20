package com.gmail.blubberalls.bingo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;

import com.google.common.collect.HashMultimap;

public class Leaderboard<T> {
    private HashMap<T, Integer> scores = new HashMap<T, Integer>();

    public int getScore(T key) {
        return scores.get(key);
    }

    public int getScoreAtPlacement(int placement) {
        ArrayList<Integer> orderedScores = getOrderedScores();
        ArrayList<Integer> placements = new ArrayList<Integer>();
        
        for (int i : orderedScores) {
            if (!placements.contains(i)) {
                placements.add(i);
            }
        }

        return placements.get(placement);
    }

    public Set<T> getWhoHasScore(int score) {
        HashMultimap<Integer, T> entriesByScore = getEntriesByScores();

        return entriesByScore.get(score);
    }

    public int getSize() {
        return scores.size();
    }

    public HashMultimap<Integer, T> getEntriesByScores() {
        HashMultimap<Integer, T> entriesByScore = HashMultimap.create();

        for (T key : scores.keySet()) {
            entriesByScore.put(scores.get(key), key);
        }

        return entriesByScore;
    }

    public ArrayList<Integer> getOrderedScores() {
        ArrayList<Integer> orderedScores = new ArrayList<Integer>();

        for (int i : scores.values()) {
            orderedScores.add(i);
        }

        Collections.sort(orderedScores, (a, b) -> b - a);

        return orderedScores;
    }

    public void setScore(T key, int score) {
        Bukkit.getLogger().info(" SCORE " + score);
        scores.put(key, score);
    }
}
