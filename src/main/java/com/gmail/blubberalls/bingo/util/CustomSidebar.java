package com.gmail.blubberalls.bingo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import com.gmail.blubberalls.bingo.Bingo;

import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class CustomSidebar {
    private static String OBJECTIVE_NAME = "sidebar";

    private static PacketContainer getCreateObjectivePacket(String objectiveName, String title) {
        PacketContainer objectivePacket = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);

        objectivePacket.getStrings().write(0, objectiveName);
        objectivePacket.getChatComponents().write(0, WrappedChatComponent.fromText(title));
        objectivePacket.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class).write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
        objectivePacket.getIntegers().write(0, 0);

        return objectivePacket;
    }

    private static PacketContainer getDeleteObjectivePacket(String objectiveName) {
        PacketContainer deleteObjectivePacket = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);

        deleteObjectivePacket.getStrings().write(0, objectiveName);
        deleteObjectivePacket.getChatComponents().write(0, WrappedChatComponent.fromText(""));
        deleteObjectivePacket.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class).write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
        deleteObjectivePacket.getIntegers().write(0, 1);

        return deleteObjectivePacket;
    }

    private static PacketContainer getDisplayPacket(String objectiveName) {
        PacketContainer displayPacket = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);

        displayPacket.getIntegers().write(0, 1);
        displayPacket.getStrings().write(0, objectiveName);

        return displayPacket;
    }

    private static PacketContainer getScorePacket(String objectiveName, String entry, int score) {
        PacketContainer setScorePacket = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);

        setScorePacket.getStrings().write(0, entry);
        setScorePacket.getStrings().write(1, objectiveName);
        setScorePacket.getIntegers().write(0, score);
        setScorePacket.getScoreboardActions().write(0, ScoreboardAction.CHANGE);

        return setScorePacket;
    }

    public static void setPlayerSidebar(Player p, String title, Map<String, Integer> scoreboardValues) {
        PacketContainer deleteObjectivePacket = getDeleteObjectivePacket(OBJECTIVE_NAME);
        PacketContainer createObjectivePacket = getCreateObjectivePacket(OBJECTIVE_NAME, title);
        PacketContainer displayPacket = getDisplayPacket(OBJECTIVE_NAME);
        ArrayList<PacketContainer> setScorePackets = new ArrayList<PacketContainer>();
        
        for (String s : scoreboardValues.keySet()) {
            setScorePackets.add(getScorePacket(OBJECTIVE_NAME, s, scoreboardValues.get(s)));
        }

        Bingo.getProtocolManager().sendServerPacket(p, deleteObjectivePacket);
        Bingo.getProtocolManager().sendServerPacket(p, createObjectivePacket);
        Bingo.getProtocolManager().sendServerPacket(p, displayPacket);
        setScorePackets.forEach((packet) -> Bingo.getProtocolManager().sendServerPacket(p, packet));
    }

    public static void setPlayerSidebar(Player p, String title, Collection<String> strings) {
        HashMap<String, Integer> scoreboardValues = new HashMap<String, Integer>();
        int i = strings.size();

        for (String s : strings) {
            scoreboardValues.put(s, i--);
        }

        setPlayerSidebar(p, title, scoreboardValues);
    }

    public static void setPlayerSidebar(Player p, String title, String... string) {
        setPlayerSidebar(p, title, Arrays.asList(string));
    }

    public static void resetPlayerSidebar(Player p) {
        Objective sidebarObjective = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        
        Bingo.getProtocolManager().sendServerPacket(p, getDeleteObjectivePacket(OBJECTIVE_NAME));
        
        if (sidebarObjective != null) {
            Bingo.getProtocolManager().sendServerPacket(p, getDisplayPacket(sidebarObjective.getName()));
        }
    }
}
