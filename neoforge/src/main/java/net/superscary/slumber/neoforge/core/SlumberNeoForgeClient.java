package net.superscary.slumber.neoforge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class SlumberNeoForgeClient extends SlumberNeoForgeBase {

    public SlumberNeoForgeClient(ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
