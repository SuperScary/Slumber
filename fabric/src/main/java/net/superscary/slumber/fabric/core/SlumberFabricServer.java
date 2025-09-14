package net.superscary.slumber.fabric.core;

import net.minecraft.world.level.Level;

public class SlumberFabricServer extends SlumberFabricBase {

    public SlumberFabricServer() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return null;
    }
}

