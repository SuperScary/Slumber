package net.superscary.slumber.fabric.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class SlumberFabricClient extends SlumberFabricBase {

    public SlumberFabricClient() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}

