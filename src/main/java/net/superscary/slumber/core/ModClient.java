package net.superscary.slumber.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class ModClient extends Base {

    public ModClient (ModContainer container, IEventBus modEventBus) {
        super(container, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
