package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.Goals;
import com.gmail.blubberalls.bingo.util.CustomSidebar;
import com.gmail.blubberalls.bingo.util.NBTUtils;
import com.gmail.blubberalls.bingo.util.TextComponents;
import com.gmail.blubberalls.bingo.util.TextUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultimap;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTFile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;

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
    private HashMap<ChatColor, Team> allTeamsByColor = new HashMap<ChatColor, Team>();
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
            allTeamsByColor.put(color, team);
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

    public NBTCompound getPlayerData(OfflinePlayer op, boolean create) {
        return create ? playerData.getOrCreateCompound(op.getUniqueId().toString()) : playerData.getCompound(op.getUniqueId().toString());
    }

    public NBTCompound getPlayerData(OfflinePlayer op) {
        return getPlayerData(op, false);
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

    public Random getRandom() {
        return random;
    }

    public boolean hasStarted() {
        return goalData.size() > 0;
    }

    public boolean isPlayerPlaying(OfflinePlayer op) {
        return op != null && getPlayerData(op) != null;
    }

    public boolean isPlayerSubscribed(OfflinePlayer op, Goal g) {
        return getPlayerData(op).getStringList("subscriptions").contains(g.getName());
    }

    public Team getTeam(OfflinePlayer op) {
        return isPlayerPlaying(op) ? getTeam(getPlayerData(op).getString("team")) : null;
    }

    public Team getTeam(String name) {
        return scoreboard.getTeam(name);
    }

    public Team getTeam(ChatColor color) {
        return allTeamsByColor.get(color);
    }
    
    public Collection<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<Team>();

        for (Team t : allTeamsByColor.values()) {
            if (getAllTeamPlayers(t).size() > 0) {
                teams.add(t);
            }
        }

        return teams;
    }

    public Leaderboard<Team, Integer> getLeaderboard() {
        Leaderboard<Team, Integer> leaderboard = new Leaderboard<Team, Integer>();

        for (Goal g : goals.values()) {
            Team whoCompleted = g.getWhoCompleted();
            int currentScore = leaderboard.containsKey(whoCompleted) ? leaderboard.get(whoCompleted) : 0;

            if (whoCompleted == null) continue;

            leaderboard.put(whoCompleted, currentScore + 1);
        }

        return leaderboard;
    }

    public Collection<Team> getWinners() {
        Collection<Team> teams = getTeams();

        if (teams.size() < 2) return null;
        
        HashMap<Team, Integer> teamToPoints = new HashMap<Team, Integer>();
        HashMultimap<Integer, Team> pointsToTeam = HashMultimap.create();
        ArrayList<Integer> allPoints = new ArrayList<Integer>();
        int freeGoals = goals.size();

        for (Goal g : goals.values()) {
            if (!g.isCompleted()) continue;
            
            Team completor = g.getWhoCompleted();
            int currentPoints = teamToPoints.getOrDefault(completor, 0);

            teamToPoints.put(completor, currentPoints + 1);
            freeGoals--;
        }
        
        for (Team t : teams) {
            int currentPoints = teamToPoints.getOrDefault(t, 0);
            
            pointsToTeam.put(currentPoints, t);
            allPoints.add(currentPoints);
        }

        Collections.sort(allPoints, (a, b) -> b - a);

        int topScore = allPoints.get(0);
        int secondPlace = allPoints.get(1);
        boolean hasWinner = secondPlace + freeGoals < topScore || freeGoals == 0;

        return hasWinner ? pointsToTeam.get(topScore) : null;
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

    public List<Player> getPlayersNotInTeam(Team t) {
        ArrayList<Player> players = new ArrayList<Player>();

        for (Player p : getPlayers()) {
            if (!t.equals(getTeam(p))) {
                players.add(p);
            }
        }

        return players;
    }
    
    public List<OfflinePlayer> getAllPlayersNotInTeam(Team t) {
        ArrayList<OfflinePlayer> oPlayers = new ArrayList<OfflinePlayer>();

        for (OfflinePlayer op : getAllPlayers()) {
            if (!t.equals(getTeam(op))) {
                oPlayers.add(op);
            }
        }

        return oPlayers;

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

    public boolean allTeamMembersMatchCondition(Team t, Predicate<Player> condition) {
        return getTeamPlayers(t).stream().allMatch(condition);
    }

    public boolean anyTeamMemberMatchesCondition(Team t, Predicate<Player> condition) {
        return getTeamPlayers(t).stream().anyMatch(condition);
    }

    public Collection<Goal> getPlayerSubscribedGoals(Player p) {
        ArrayList<Goal> subscribedGoals = new ArrayList<Goal>();

        getPlayerData(p).getStringList("subscriptions").forEach(goalName -> subscribedGoals.add(getGoal(goalName)));

        return subscribedGoals;
    }

    public BaseComponent getPrefix() {
        return new TextComponent(ChatColor.AQUA + "[Bingo]" + ChatColor.RESET + " ");
    }

    public BaseComponent[] getMessageWithPrefix(BaseComponent... components) {
        BaseComponent[] prefixed = new BaseComponent[components.length + 1];
        
        for (int i = 0; i < components.length; i++) {
            prefixed[i + 1] = components[i];
        }

        prefixed[0] = getPrefix();

        return prefixed;
    }

    public BaseComponent[] getMessageWithPrefix(String s) {
        return getMessageWithPrefix(new TextComponent(s));
    }

    public BaseComponent[] getBoard(Player p) {
        Team t = getTeam(p);
        ComponentBuilder builder = new ComponentBuilder();
        Iterator<Goal> goalIterator = goals.values().iterator();

        for (int y = 0; y < getWidth() && goalIterator.hasNext(); y++) {            
            ArrayList<Goal> goalRow = new ArrayList<Goal>();
            
            for (int x = 0; x < getLength() && goalIterator.hasNext(); x++) {
                goalRow.add(goalIterator.next());
            }
            
            for (int x = 0; x < goalRow.size(); x++) {
                builder.append(goalRow.get(x).getIcon(), FormatRetention.NONE);
                builder.append(TextComponents.offset(6), FormatRetention.NONE);
            }

            builder.append("\n");

            for (int x = 0; x < goalRow.size(); x++) {
                builder.append(goalRow.get(x).getClickboxFor(t), FormatRetention.NONE);
            }

            if (goalIterator.hasNext()) {
                builder.append("\n\n", FormatRetention.NONE);
            }
        }

        builder.append("\n\n", FormatRetention.NONE);

        return builder.create();
    }

    public void addPlayer(Player p, Team t) {
        if (isPlayerPlaying(p)) {
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
        t.removeEntry(p.getName());
        playerData.removeKey(p.getUniqueId().toString());
        getTeamData(t).getStringList("players").remove(p.getUniqueId().toString());
        CustomSidebar.resetPlayerSidebar(p);
    }

    public void updatePlayerSidebar(Player p) {
        Collection<Goal> subscribedGoals = getPlayerSubscribedGoals(p);

        if (subscribedGoals.size() > 0) {
            ArrayList<String> strings = new ArrayList<String>();

            for (Goal g : subscribedGoals) {
                strings.add(g.getSidebarTitleFor(getTeam(p)));
            }

            CustomSidebar.setPlayerSidebar(p, "Bingo", strings);
        }
        else {
            CustomSidebar.resetPlayerSidebar(p);
        }
    }

    public void broadcastMessage(Collection<Player> players, BaseComponent... message) {
        players.forEach(player -> player.spigot().sendMessage(message));
    }

    public void broadcastMessage(Collection<Player> players, String s) {
        broadcastMessage(players, new TextComponent(s));
    }

    public void broadcastMessage(BaseComponent... message) {
        broadcastMessage(getPlayers(), message);
    }

    public void broadcastMessage(String message) {
        broadcastMessage(getPlayers(), message);
    }

    public void broadcastSound(Collection<Player> players, Sound sound) {
        players.forEach(player -> player.playSound(player.getLocation(), sound, 1F, 1F));
    }

    public void unregisterGoalEvents() {
        goals.values().forEach(Goal::unloadEvents);
    }

    public void setPlayerGoalSubscription(OfflinePlayer op, Goal g, boolean subscribed) {
        if (subscribed && !isPlayerSubscribed(op, g)) {
            getPlayerData(op).getStringList("subscriptions").add(g.getName());
        }
        else if (!subscribed) {
            getPlayerData(op).getStringList("subscriptions").remove(g.getName());
        }

        if (op.isOnline()) {
            updatePlayerSidebar(op.getPlayer());
        }
    }

    public void update() {
        if (!hasStarted()) return;

        getPlayers().forEach(this::updatePlayerSidebar);

        Collection<Team> winners = getWinners();

        if (winners != null) {
            endGame(winners);
        }
    }

    public void newGame(int length, int width) {
        endGame();
        data.setInteger("length", length);
        data.setInteger("width", width);

        for (Goal goal : Goals.randomGoals(this, length * width)) {
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
            Goal g = Goals.loadGoal(this, instanceData);

            goals.put(g.getName(), g);
            if (!g.isCompleted() || g.isCapturable()) {
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

    public void endGame(Collection<Team> winners) {                
        Team[] winnerArray = winners.toArray(new Team[winners.size()]);

        if (winnerArray.length == 1) {
            Team winner = winnerArray[0];
            
            broadcastMessage(winner.getColor() + "" + winner.getDisplayName() + ChatColor.RESET + " has won the game!!! CONGRATULATIONS!!");
        }
        else if (winnerArray.length > 1) {
            String[] teams = new String[winnerArray.length];

            for (int i = 0; i < teams.length; i++) {
                Team t = winnerArray[i];

                teams[i] = t.getColor() + t.getDisplayName() + ChatColor.RESET;
            }

            broadcastMessage(TextUtils.getGrammaticalList(teams) + " have tied for first place, they are the winners!!! CONGRATULATIONS!!");
        }

        endGame();
    }

}
