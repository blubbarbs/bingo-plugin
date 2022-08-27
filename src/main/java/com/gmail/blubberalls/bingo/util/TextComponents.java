package com.gmail.blubberalls.bingo.util;

import net.md_5.bungee.api.chat.TranslatableComponent;

public class TextComponents {
    public static int ICON_SIZE_PX = 16;
    public static int FRAME_SIZE_PX = 26;
    
    public static TranslatableComponent ICON_OFFSET = offset(-(ICON_SIZE_PX + 1));
    public static TranslatableComponent FRAME_OFFSET = offset(-(ICON_SIZE_PX + ((FRAME_SIZE_PX - ICON_SIZE_PX) / 2) + 1));

    public static TranslatableComponent offset(int offset) {
        TranslatableComponent offsetComponent = new TranslatableComponent("space.nosplit." + offset);

        offsetComponent.setFont("space:default");

        return offsetComponent;
    }

    public static TranslatableComponent icon(String bingoText) {
        TranslatableComponent iconComponent = new TranslatableComponent(bingoText);

        iconComponent.setFont("bingo:default");

        return iconComponent;
    }
}
