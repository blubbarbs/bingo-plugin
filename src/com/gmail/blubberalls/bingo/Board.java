package com.gmail.blubberalls.bingo;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Board {
    public static final int SPACE_WIDTH = 16;
    public static final int SHADOW_WIDTH = 2;
    public static final int SPACE_SEPARATION = 2;

    private int length;
    private int width;

    public Board(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public BaseComponent[] getBoardDisplay(Player p) {
        ComponentBuilder builder = new ComponentBuilder();        
        
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                int offset = (x * SPACE_SEPARATION); 
                TranslatableComponent spaceOffset = new TranslatableComponent("offset." + offset);
                TranslatableComponent space = new TranslatableComponent("bingo.space");

                spaceOffset.setFont("space:default");
                spaceOffset.addWith(space);
                space.setFont("bingo:default");
                space.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("" + x + "" + y)));
                builder.append(spaceOffset);
            }
        }
        
        for (int x = 0; x < length; x ++) {
            int offset = -(x * SPACE_WIDTH) -69; 
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

}
