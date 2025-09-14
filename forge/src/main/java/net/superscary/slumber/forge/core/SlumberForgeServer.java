package net.superscary.slumber.forge.core;

import net.minecraft.world.level.Level;

public class SlumberForgeServer extends SlumberForgeBase {

    public SlumberForgeServer() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
