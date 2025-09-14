package net.superscary.slumber.forge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class SlumberForgeClient extends SlumberForgeBase {

    public SlumberForgeClient() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
