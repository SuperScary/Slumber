package net.superscary.slumber.forge.hooks;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.superscary.slumber.Slumber;
import net.superscary.slumber.sim.Simulator;

@Mod.EventBusSubscriber(modid = Slumber.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSleepHook {

    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent.Post event) {
        Simulator.simulateWorld();
    }

}
