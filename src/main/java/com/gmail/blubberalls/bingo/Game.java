package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.GoalFactories;
import com.gmail.blubberalls.bingo.util.CustomSidebar;
import com.gmail.blubberalls.bingo.util.NBTUtils;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTFile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class Game {
    public static ChatColor[] TEAM_COLORS = {
        ChatColor.AQUA,
        ChatColor.BLACK,
        ChatColor.BLUE,
        ChatColor.DARK_AQUA,
        ChatColor.DARK_BLUE,
        ChatColor.DARK_GRAY,
        ChatColor.DARK_GREEN,
        ChatColor.DARK_PURPLE,
        ChatColor.DARK_RED,
        ChatColor.GOLD,
        ChatColor.GRAY,
        ChatColor.GREEN,
        ChatColor.LIGHT_PURPLE,
        ChatColor.RED,
        ChatColor.WHITE,
        ChatColor.YELLOW
    };

    private File dataFile;
    private NBTFile data;
    private NBTCompoundList goalData;
    private NBTCompound teamData;
    private NBTCompound playerData;
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private HashMap<String, Goal> goals = new HashMap<String, Goal>();
    private HashMap<ChatColor, Team> teamsByColor = new HashMap<ChatColor, Team>();
    private Leaderboard<Team> leaderboard = new Leaderboard<Team>();
    private Random random = new Random();

    public Game() {
        this.dataFile = new File(Bingo.getInstance().getDataFolder(), "game_data.nbt");
        
        try {
            this.data = new NBTFile(dataFile);
            this.goalData = data.getCompoundList("goals");
            this.playerData = data.getOrCreateCompound("player_data");
            this.teamData = data.getOrCreateCompound("team_data");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (ChatColor color : Game.TEAM_COLORS) {
            Team team = scoreboard.registerNewTeam(color.name());

            team.setDisplayName(TextUtils.capitalizeFirstLetters(color.name(), "_", " "));
            team.setColor(color);
            team.setCanSeeFriendlyInvisibles(true);
            teamsByColor.put(color, team);
            teamData.getOrCreateCompound(team.getName());
        }
    }

    public File getDataFile() {
        return dataFile;
    }

    public NBTFile getData() {
        return data;
    }

    public NBTCompoundList getGoalData() {
        return goalData;
    }

    public NBTCompound getPlayerData(Player p, boolean create) {
        return create ? playerData.getOrCreateCompound(p.getUniqueId().toString()) : playerData.getCompound(p.getUniqueId().toString());
    }

    public NBTCompound getPlayerData(Player p) {
        return getPlayerData(p, false);
    }

    public NBTCompound getTeamData(Team t, boolean create) {
        return create ? teamData.getOrCreateCompound(t.getName()) : teamData.getCompound(t.getName());
    }

    public NBTCompound getTeamData(Team t) {
        return getTeamData(t, true);
    }

    public int getLength() {
        return data.getInteger("length");
    }

    public int getWidth() {
        return data.getInteger("width");
    }

    public Goal getGoal(String name) {
        return goals.get(name);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Leaderboard<Team> getLeaderboard() {
        return leaderboard;
    }

    public Random getRandom() {
        return random;
    }

    public boolean hasStarted() {
        return goalData.size() > 0;
    }

    public Collection<Team> getTeams() {
        return teamsByColor.values();
    }

    public boolean isPlaying(Player p) {
        return p != null && getPlayerData(p) != null;
    }

    public Team getTeam(Player p) {
        return isPlaying(p) ? getTeam(getPlayerData(p).getString("team")) : null;
    }

    public Team getTeam(String name) {
        return scoreboard.getTeam(name);
    }

    public Team getTeam(ChatColor color) {
        return teamsByColor.get(color);
    }
    
    public Collection<Team> getAllTeams() {
        return teamsByColor.values();
    }

    public int getTeamScore(Team t) {
        int score = 0;

        for (Goal g : goals.values()) {
            if (t.equals(g.getWhoCompleted())) {
                score++;
            }
        }
    
        return score;
    }

    public int getNumGoalsLeft() {
        int freeGoals = 0;

        for (Goal g : goals.values()) {
            if (!g.isCompleted()) {
                freeGoals++;
            }
        }

        return freeGoals;
    }

    public List<OfflinePlayer> getAllPlayers() {
        ArrayList<OfflinePlayer> offlinePlayers = new ArrayList<OfflinePlayer>();

        for (String uuidString : playerData.getKeys()) {
            UUID uuid = UUID.fromString(uuidString);

            offlinePlayers.add(Bukkit.getOfflinePlayer(uuid));
        }

        return offlinePlayers;
    }

    public List<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        for (OfflinePlayer op : getAllPlayers()) {
            if (op != null && op.isOnline()) {
                players.add(op.getPlayer());
            }
        }

        return players;
    }

    public List<OfflinePlayer> getAllTeamPlayers(Team t) {
        ArrayList<OfflinePlayer> offlinePlayers = new ArrayList<OfflinePlayer>();

        for (String uuidString : getTeamData(t).getStringList("players")) {            
            UUID uuid = UUID.fromString(uuidString);
            
            offlinePlayers.add(Bukkit.getOfflinePlayer(uuid));
        }

        return offlinePlayers;
    }

    public List<Player> getTeamPlayers(Team t) {
        ArrayList<Player> players = new ArrayList<Player>();

        for (OfflinePlayer op : getAllTeamPlayers(t)) {
            if (op != null && op.isOnline()) {
                players.add(op.getPlayer());
            }
        }

        return players;
    }

    public Collection<Goal> getPlayerSubscribedGoals(Player p) {
        ArrayList<Goal> subscribedGoals = new ArrayList<Goal>();

        subscribedGoals.addAll(goals.values());
        //getPlayerData(p).getStringList("subscriptions").forEach(goalName -> subscribedGoals.add(getGoal(goalName)));

        return subscribedGoals;
    }

    public BaseComponent getPrefix() {
        return new TextComponent(ChatColor.AQUA + "[Bingo]" + ChatColor.RESET + " ");
    }

    public BaseComponent[] getMessageWithPrefix(String s) {
        BaseComponent[] prefixed = new BaseComponent[2];
        
        prefixed[0] = getPrefix();
        prefixed[1] = new TextComponent(s);

        return prefixed;
    }

    public BaseComponent[] getMessageWithPrefix(BaseComponent[] components) {
        BaseComponent[] prefixed = new BaseComponent[components.length + 1];
        
        for (int i = 0; i < components.length; i++) {
            prefixed[i + 1] = components[i];
        }

        prefixed[0] = getPrefix();

        return prefixed;
    }

    public BaseComponent[] getBoard(Player p) {
        ComponentBuilder builder = new ComponentBuilder();

        goals.values().forEach(goal -> builder.append(goal.getTextIcon()));
        
        return builder.create();
    }

    public void addPlayer(Player p, Team t) {
        if (isPlaying(p)) {
            removePlayer(p);
        }

        t.addEntry(p.getName());
        p.setScoreboard(scoreboard);
        getPlayerData(p, true).setString("team", t.getName());
        getTeamData(t).getStringList("players").add(p.getUniqueId().toString());
        updatePlayerSidebar(p);
    }

    public void removePlayer(Player p) {
        Team t = getTeam(p);
        
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        CustomSidebar.resetPlayerSidebar(p);
        t.removeEntry(p.getName());
        playerData.removeKey(p.getUniqueId().toString());
        getTeamData(t).getStringList("players").remove(p.getUniqueId().toString());
    }

    public void updatePlayerSidebar(Player p) {
        Collection<Goal> subscribedGoals = getPlayerSubscribedGoals(p);

        if (subscribedGoals.size() > 0) {
            ArrayList<String> strings = new ArrayList<String>();

            for (Goal g : subscribedGoals) {
                strings.add(g.getTeamCompletionStatus(getTeam(p)));
            }

            CustomSidebar.setPlayerSidebar(p, "Bingo", strings);
        }
        else {
            CustomSidebar.resetPlayerSidebar(p);
        }
    }

    public void broadcastMessage(Team t, String s) { 
        getTeamPlayers(t).forEach(p -> p.spigot().sendMessage(getMessageWithPrefix(s)));
    }

    public void broadcastMessage(Team t, BaseComponent[] message) { 
        getTeamPlayers(t).forEach(p -> p.spigot().sendMessage(getMessageWithPrefix(message)));
    }

    public void broadcastMessage(String s) {
        getPlayers().forEach(p -> p.spigot().sendMessage(getMessageWithPrefix(s)));
    }

    public void broadcastMessage(BaseComponent[] message) {
        getPlayers().forEach(p -> p.spigot().sendMessage(getMessageWithPrefix(message)));
    }

    public void unregisterGoalEvents() {
        goals.values().forEach(Goal::unloadEvents);
    }

    public void update() {
        if (!hasStarted()) return;

        getPlayers().forEach(this::updatePlayerSidebar);
        getTeams().forEach(team -> leaderboard.setScore(team, getTeamScore(team)));

        int freeGoals = getNumGoalsLeft();
        List<Integer> orderedScores = leaderboard.getOrderedScores();
        int topScore = orderedScores.get(0);
        int nextScore = orderedScores.get(1);
        boolean shouldEndGame = nextScore + freeGoals < topScore || freeGoals == 0;

        Bukkit.getLogger().info(" FREE GOALS " + freeGoals + " TOP SCORE " + topScore + " NEXT SCORE " + nextScore);

        if (shouldEndGame) {
            Set<Team> winners = leaderboard.getWhoHasScore(topScore);

            endGame(winners.toArray(new Team[winners.size()]));
        }
    }

    public void newGame(int length, int width) {
        endGame();
        data.setInteger("length", length);
        data.setInteger("width", width);

        for (Goal goal : GoalFactories.randomGoals(this, length * width)) {
            goals.put(goal.getName(), goal);
            goal.loadEvents();
        }

        update();
    }

    public void saveGame() {
        try {
            data.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        for (NBTCompound instanceData : goalData) {
            Goal g = GoalFactories.loadGoal(this, instanceData);

            goals.put(g.getName(), g);
            if (!g.isCompleted() || g.hasEventsWhenCompleted()) {
                g.loadEvents();
            }
        }

        update();
    }

    public void endGame() {
        getPlayers().forEach(p -> removePlayer(p));
        unregisterGoalEvents();
        goalData.clear();
        NBTUtils.clearNBT(playerData);
        NBTUtils.clearNBT(teamData);
        goals.clear();
    }

    public void endGame(Team[] winners) {                
        if (winners.length == 1) {
            Team winner = winners[0];
            
            broadcastMessage(winner.getColor() + "" + winner.getDisplayName() + " has won the game!!! CONGRATULATIONS!!");
        }
        else if (winners.length > 1) {
            String[] teams = new String[winners.length];

            for (int i = 0; i < teams.length; i++) {
                Team t = winners[i];

                teams[i] = t.getColor() + t.getDisplayName() + ChatColor.RESET;
            }

            broadcastMessage(TextUtils.getGrammaticalList(teams) + " have won!!! CONGRATULATIONS!!");
        }

        endGame();
    }

}
