package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.GoalFactories;
import com.gmail.blubberalls.bingo.util.CustomSidebar;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTFile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

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

    public Team getTeam(Player p) {
        Team t = p.getScoreboard().getEntryTeam(p.getName());

        if (t == null) {
            t = p.getScoreboard().registerNewTeam(p.getUniqueId().toString());
            
            t.setDisplayName(p.getDisplayName());
            t.addEntry(p.getName());
        }

        return t;
    }

    public List<Player> getTeamPlayers(Team t) {
        ArrayList<Player> players = new ArrayList<Player>();
    
        for (String name : t.getEntries()) {
            Player p = Bukkit.getPlayer(name);

            if (p != null) {
                players.add(p);
            }
        }
    
        return players;
    }

    public BaseComponent getPrefix() {
        return new TextComponent(ChatColor.AQUA + "[Bingo]" + ChatColor.RESET + " ");
    }

    public BaseComponent[] withPrefix(String s) {
        BaseComponent[] prefixed = new BaseComponent[2];
        
        prefixed[0] = getPrefix();
        prefixed[1] = new TextComponent(s);

        return prefixed;
    }

    public BaseComponent[] withPrefix(BaseComponent[] components) {
        BaseComponent[] prefixed = new BaseComponent[components.length + 1];
        
        for (int i = 0; i < components.length; i++) {
            prefixed[i + 1] = components[i];
        }

        prefixed[0] = getPrefix();

        return prefixed;
    }

    public BaseComponent[] getBoard(Player p) {
        ComponentBuilder builder = new ComponentBuilder();

        for (Goal g : goals) {
            builder.append(g.getTextIcon());
        }

        return builder.create();
    }

    public Collection<Goal> getPlayerSubscribedGoals(Player p) {
        ArrayList<Goal> subscribedGoals = new ArrayList<Goal>();
        
        for (Goal g : goals) {
            if (g.isPlayerSubscribed(p)) {
                subscribedGoals.add(g);
            }
        }

        return subscribedGoals;
    }

    public void updatePlayerSidebar(Player p) {
        Collection<Goal> subscribedGoals = getPlayerSubscribedGoals(p);

        if (subscribedGoals.size() > 0) {
            ArrayList<String> strings = new ArrayList<String>();

            for (Goal g : subscribedGoals) {
                strings.add(g.getTeamCompletionStatus(g.getTeam(p)));
            }

            CustomSidebar.setPlayerSidebar(p, "Bingo", strings);
        }
        else {
            CustomSidebar.resetPlayerSidebar(p);
        }
    }

    public void broadcastMessage(Team t, String s) { 
        getTeamPlayers(t).forEach(p -> p.spigot().sendMessage(withPrefix(s)));
    }

    public void broadcastMessage(Team t, BaseComponent[] message) { 
        getTeamPlayers(t).forEach(p -> p.spigot().sendMessage(withPrefix(message)));
    }

    public void broadcastMessage(String s) {
        Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(withPrefix(s)));
    }

    public void broadcastMessage(BaseComponent[] message) {
        Bukkit.getOnlinePlayers().forEach(p -> p.spigot().sendMessage(withPrefix(message)));
    }

    public void unregisterGoalEvents() {
        goals.forEach((goal) -> goal.unloadEvents());
    }

    public void update() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getTeam(p) == null) continue;
            
            updatePlayerSidebar(p);
        }
    }

    public void newGame(int length, int width) {
        endGame();
        data.setInteger("length", length);
        data.setInteger("width", width);

        for (Goal g : GoalFactories.randomGoals(this, length * width)) {
            data.getCompoundList("goals").addCompound(g.getSavedData());
            goals.add(g);
            g.loadEvents();
        }

        update();
    }

    public void saveGame() {
        data.removeKey("goals");
        goals.forEach((g) -> data.getCompoundList("goals").addCompound(g.getSavedData()));

        try {
            data.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        Bukkit.getLogger().info("Loading...");

        for (NBTCompound instanceData : data.getCompoundList("goals")) {
            Goal g = GoalFactories.loadGoal(this, instanceData);

            goals.add(g);
            
            if (g.shouldRegisterEvents()) {
                g.loadEvents();
            }
        }

        update();
    }

    public void endGame() {
        unregisterGoalEvents();
        data.removeKey("length");
        data.removeKey("width");
        data.removeKey("goals");
        goals.clear();
    }
}
