package net.superscary.slumber.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class ModServer extends Base {

    public ModServer (ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
