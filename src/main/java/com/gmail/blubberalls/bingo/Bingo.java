package com.gmail.blubberalls.bingo;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Bingo extends JavaPlugin {
    private static Bingo INSTANCE;
    private static Game GAME;

    public static Bingo getInstance() {
        return INSTANCE;
    }

    public static Game getGame() {
        return GAME;
    }

    @Override
    public void onEnable() {        
        INSTANCE = this;
        GAME = new Game();

        GAME.loadGame();
    }   

    @Override
    public void onDisable() {
        GAME.saveGame();
    }

    public BaseComponent[] getSpaceComponents() {
        ComponentBuilder builder = new ComponentBuilder();

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x ++) {
                int offset = (x * 2); 
                TranslatableComponent spaceOffset = new TranslatableComponent("offset." + offset);
                TranslatableComponent space = new TranslatableComponent("bingo.space");

                spaceOffset.setFont("space:default");
                spaceOffset.addWith(space);
                space.setFont("bingo:default");
                space.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("" + x + "" + y)));
                builder.append(spaceOffset);
            }
            
            for (int x = 0; x < 5; x ++) {
                int offset = -(x * 15) -69; 
                TranslatableComponent numberOffset = new TranslatableComponent("offset." + (offset - 16));
                TranslatableComponent number = new TranslatableComponent("bingo.num.1");
                TranslatableComponent statusOffset = new TranslatableComponent("offset." + (offset - 32));
                TranslatableComponent status = new TranslatableComponent("bingo.blank");

                numberOffset.setFont("space:default");
                numberOffset.addWith(number);
                number.setFont("bingo:default");
                statusOffset.setFont("space:default");
                statusOffset.addWith(status); 
                status.setFont("bingo:default");     
                builder.append(numberOffset);
                builder.append(statusOffset);
            }

            builder.append("\n\n");
        }

        return builder.create();
    }
}