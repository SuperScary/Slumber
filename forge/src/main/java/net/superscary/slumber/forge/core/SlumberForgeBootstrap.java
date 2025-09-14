package net.superscary.slumber.forge.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.superscary.slumber.Slumber;

@Mod(Slumber.MOD_ID)
public class SlumberForgeBootstrap {

    public SlumberForgeBootstrap() {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new SlumberForgeClient();
            case DEDICATED_SERVER -> new SlumberForgeServer();
        }
    }

}
