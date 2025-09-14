package net.superscary.slumber.neoforge.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class SlumberNeoForgeServer extends SlumberNeoForgeBase {

    public SlumberNeoForgeServer (ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
