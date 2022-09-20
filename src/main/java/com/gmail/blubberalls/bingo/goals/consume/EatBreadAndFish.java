package com.gmail.blubberalls.bingo.goals.consume;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import com.gmail.blubberalls.bingo.goal.Goal;
import com.gmail.blubberalls.bingo.goal.goal_data.ScoreData;
import com.gmail.blubberalls.bingo.util.Icons;

import net.md_5.bungee.api.ChatColor;

public class EatBreadAndFish extends Goal implements ScoreData {
    
    @Override
    public String getTitle() {
        return "Anti-Christ";
    }

    @Override
    public String getIconPath() {
        return Icons.ITEM("bread");
    }

    @Override
    public String getDescription() {
        return "Eat 5 Bread and 2 Cod.";
    }

    public int getBreadGoal() {
        return 5;
    }

    public int getFishGoal() {
        return 2;
    }
    
    public int getBreadScoreFor(Team t) {
        int score = getScoreFor(t, "bread");

        return score >= getBreadGoal() ? getBreadGoal() : score;
    }

    public int getFishScoreFor(Team t) {
        int score = getScoreFor(t, "cod");

        return score >= getFishGoal() ? getFishGoal() : score;
    }

    @Override
    public String getSidebarTitleFor(Team t) {
        Team completor = getWhoCompleted();
        String sidebar = super.getSidebarTitleFor(t);

        if (completor == null) {
            sidebar += ChatColor.RESET +"\n> Bread Eaten: " + ChatColor.AQUA + getBreadScoreFor(t) + "/" + getBreadGoal();
            sidebar += ChatColor.RESET + "\n> Fish Eaten: " + ChatColor.AQUA + getFishScoreFor(t) + "/" + getFishGoal();
        }
        
        return sidebar;
    }

    @Override
    public String getProgressDescriptionFor(Team t) {
        String desc = ChatColor.RESET +"> Bread Eaten: " + ChatColor.AQUA + getBreadScoreFor(t) + "/" + getBreadGoal();
        desc += ChatColor.RESET + "\n> Fish Eaten: " + ChatColor.AQUA + getFishScoreFor(t) + "/" + getFishGoal();

        return desc;
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        ItemStack eatenItem = event.getItem();
        
        if (eatenItem.getType() != Material.BREAD && eatenItem.getType() != Material.COD) return;

        addScoreFor(event.getPlayer(), eatenItem.getType().name().toLowerCase(), 1);
        game.getTeamPlayers(game.getTeam(event.getPlayer())).forEach(game::updatePlayerSidebar);

        if (getScoreFor(event.getPlayer(), "bread") < getBreadGoal()
        ||  getScoreFor(event.getPlayer(), "cod") < getFishGoal()) return;

        setCompletedFor(event.getPlayer());
    }
}
