package net.superscary.slumber.fabric.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class SlumberFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // Bootstrap platform-specific base based on environment
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            new SlumberFabricClient();
        } else {
            new SlumberFabricServer();
        }
    }
}
