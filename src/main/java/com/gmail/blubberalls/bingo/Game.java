package com.gmail.blubberalls.bingo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;

import com.gmail.blubberalls.bingo.goal.Goal;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTFile;
import net.md_5.bungee.api.chat.BaseComponent;

public class Game {
    private File dataFile;
    private NBTCompound data;
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private Random random = new Random();
    
    public Game() {
        this.dataFile = new File(Bingo.getInstance().getDataFolder(), "data.nbt");
        this.data = new NBTContainer();
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

    public void newGame(int length, int width) {
        data.removeKey("length");
        data.removeKey("width");
        data.removeKey("goals");
        data.setInteger("length", length);
        data.setInteger("width", width);
        goals.clear();

        Collection<Goal> newGoals = Goals.randomGoals(this, length * width);

        for (Goal g : newGoals) {
            data.getCompoundList("goals").addCompound(g.getData());
            goals.add(g);
            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
        }
    }

    public void saveGame() {
        try {
            if (!this.getDataFile().exists()) {
                this.getDataFile().getParentFile().mkdirs();
                this.getDataFile().createNewFile();
            }
        
            FileOutputStream oStream = new FileOutputStream(this.getDataFile());
            data.writeCompound(oStream);
            oStream.close();            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try {
            NBTFile fileData = new NBTFile(this.getDataFile());

            data.mergeCompound(fileData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }                
        
        NBTCompoundList goalDatas = data.getCompoundList("goals");

        for (NBTCompound goalData : goalDatas) {
            Goal g = Goals.loadGoal(this, goalData);

            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
            goals.add(g);
        }        
    }
}
