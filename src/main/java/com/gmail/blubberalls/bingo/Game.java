package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.map.MinecraftFont;
import org.bukkit.scoreboard.Objective;

import com.gmail.blubberalls.bingo.custom_sidebar.CustomSidebar;
import com.gmail.blubberalls.bingo.goal.Goal;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTFile;
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

    public Collection<Goal> getSubscribedGoals(Player p) {
        ArrayList<Goal> subscribedGoals = new ArrayList<Goal>();
        
        for (Goal g : goals) {
            if (g.isSubscribed(p)) {
                subscribedGoals.add(g);
            }
        }

        return subscribedGoals;
    }

    public Objective getPlayerObjective(Player p) {
        String objectiveName = "bingo_sidebar." + p.getUniqueId().toString();
        
        return p.getScoreboard().getObjective(objectiveName) != null ? p.getScoreboard().getObjective(objectiveName) : p.getScoreboard().registerNewObjective(objectiveName, "dummy", "Bingo");
    }

    public Objective resetPlayerObjective(Player p) {        
        getPlayerObjective(p).unregister();

        return getPlayerObjective(p);
    }

    public void updatePlayerSidebar(Player p) {
        Collection<Goal> subscribedGoals = getSubscribedGoals(p);

        Bukkit.getLogger().info("" + subscribedGoals.size());

        if (subscribedGoals.size() > 0) {
            ArrayList<String> strings = new ArrayList<String>();
            int maxWidth = 0;

            for (Goal g : subscribedGoals) {
                String title = g.getTitle();
                String completion = g.getCompletionStatus(p);
                int width = MinecraftFont.Font.getWidth(title + " " + completion);

                maxWidth = width > maxWidth ? width : maxWidth;
            }

            for (Goal g : subscribedGoals) {
                String title = g.getTitle();
                String completion = g.getCompletionStatus(p);
                int width = MinecraftFont.Font.getWidth(title + " " + completion);
                String entry = title + " " +  CustomSidebar.getPixelOffsetString(maxWidth - width) + completion;

                strings.add(entry);
            }

            CustomSidebar.setPlayerSidebar(p, "Bingo", strings);    
        }
        else {
            CustomSidebar.resetPlayerSidebar(p);
        }
    }

    public void updateAllSidebars() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePlayerSidebar(p);
        }
    }

    public void newGame(int length, int width) {
        endGame();
        data.setInteger("length", length);
        data.setInteger("width", width);

        for (Goal g : Goals.randomGoals(this, length * width)) {
            data.getCompoundList("goals").addCompound(g.getData());
            goals.add(g);
            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
        }

        updateAllSidebars();
    }

    public void saveGame() {
        data.removeKey("goals");
        goals.forEach((g) -> data.getCompoundList("goals").addCompound(g.getData()));

        try {
            data.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {        
        for (NBTCompound goalData : data.getCompoundList("goals")) {
            Goal g = Goals.loadGoal(this, goalData);

            Bukkit.getPluginManager().registerEvents(g, Bingo.getInstance());
            goals.add(g);
        }

        updateAllSidebars();
    }

    public void endGame() {
        data.removeKey("length");
        data.removeKey("width");
        data.removeKey("goals");
        goals.clear();

        HandlerList.unregisterAll(Bingo.getInstance());
    }
}
