package net.superscary.slumber.neoforge.core;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.superscary.slumber.Slumber;

@Mod(Slumber.MOD_ID)
public class SlumberNeoForgeBootstrap {

    public SlumberNeoForgeBootstrap(ModContainer container, IEventBus modEventBus) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new SlumberNeoForgeClient(container, modEventBus);
            case DEDICATED_SERVER -> new SlumberNeoForgeServer(container, modEventBus);
        }
    }

}
