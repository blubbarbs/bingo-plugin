package com.gmail.blubberalls.bingo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.core.config.builder.api.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.GoalFactories;
import com.gmail.blubberalls.bingo.util.CustomSidebar;
import com.gmail.blubberalls.bingo.util.TextUtils;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTFile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Game {
    private File dataFile;
    private NBTFile data;
    private ArrayList<Goal> goals = new ArrayList<Goal>();

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

    public BaseComponent[] getBoard() {
        ComponentBuilder builder = new ComponentBuilder();

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                TranslatableComponent icon = TextUtils.getBoardComponent(2, "bingo.icons.test");

                icon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("" + x + "" + y)));
                builder.append(icon);
            }

            builder.append(TextUtils.getOffsetComponent((-16 * 5) - (5 * 2) - 2));

            for (int x = 0; x < 5; x++) {
                TranslatableComponent o = TextUtils.getBoardComponent(0, "bingo.overlay.o");
                TranslatableComponent blank = TextUtils.getBoardComponent(-17, "bingo.blank");
                TranslatableComponent xc = TextUtils.getBoardComponent(-17, "bingo.overlay.x");

                builder.append(o);
                builder.append(blank);
                builder.append(xc);
                builder.append(TextUtils.getOffsetComponent(2));
            }

            builder.append("\n\n");
        }

        return builder.create();
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

    public void updatePlayerSidebar(Player p) {
        Collection<Goal> subscribedGoals = getSubscribedGoals(p);

        if (subscribedGoals.size() > 0) {
            ArrayList<String> strings = new ArrayList<String>();

            for (Goal g : subscribedGoals) {
                strings.add(g.getCompletionStatus(p));
            }

            CustomSidebar.setPlayerSidebar(p, "Bingo", strings);
        }
        else {
            CustomSidebar.resetPlayerSidebar(p);
        }
    }

    public void unregisterGoalEvents() {
        goals.forEach((goal) -> goal.unloadEvents());
    }

    public void update() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePlayerSidebar(p);
        }
    }

    public void newGame(int length, int width) {
        endGame();
        data.setInteger("length", length);
        data.setInteger("width", width);

        for (Goal g : GoalFactories.randomGoals(this, length * width)) {
            data.getCompoundList("goals").addCompound(g.getData());
            goals.add(g);
            g.loadEvents();
        }

        update();
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
        Bukkit.getLogger().info("Loading...");

        for (NBTCompound instanceData : data.getCompoundList("goals")) {
            Goal g = GoalFactories.loadGoal(this, instanceData);

            if (g != null) {
                goals.add(g);
                g.loadEvents();;    
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
