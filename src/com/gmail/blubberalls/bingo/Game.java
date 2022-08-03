package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;

import com.gmail.blubberalls.bingo.goal.Goal;

import dev.jorel.commandapi.nbtapi.NBTCompound;
import dev.jorel.commandapi.nbtapi.NBTCompoundList;
import dev.jorel.commandapi.nbtapi.NBTFile;
import net.md_5.bungee.api.chat.BaseComponent;

public class Game {
    private File dataFile;
    private NBTFile data;
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private Random random = new Random();
    
    public Game() {
        this.dataFile = new File(Bingo.getInstance().getDataFolder(), "data.nbt");

        try {
            this.data = new NBTFile(dataFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getDataFile() {
        return dataFile;
    }

    public NBTCompound getData() {
        return data;
    }

    public int getLength() {
        return data.getInteger("length");
    }

    public int getWidth() {
        return data.getInteger("width");
    }

    public Random getRandom() {
        return random;
    }

    public BaseComponent[] getBoard() {
        return null;
    }

    public void saveGame() {
        data.getCompoundList("goals").clear();        

        try {
            for (Goal g : goals) {
                data.getCompoundList("goals").addCompound(g.getData());
            }

            data.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public void newGame(int length, int width) {
        data.clearNBT();
        data.setInteger("length", length);
        data.setInteger("width", width);
        goals.clear();

        Collection<Goal> goals = Goals.randomGoals(this, length * width);
        
        for (Goal g : goals) {
            data.getCompoundList("goals").addCompound(g.getData());
            goals.add(g);
            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
        }
    }

    public void loadGame() {
        NBTCompoundList goalDatas = data.getCompoundList("goals");

        for (NBTCompound goalData : goalDatas) {
            Goal g = Goals.loadGoal(this, goalData);

            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
            goals.add(g);
        }        
    }
}
