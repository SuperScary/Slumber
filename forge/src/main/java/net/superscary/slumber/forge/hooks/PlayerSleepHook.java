package net.superscary.slumber.forge.hooks;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.superscary.slumber.sim.Simulator;

public class PlayerSleepHook {

    @SubscribeEvent
    public void onServerTick(final TickEvent.ServerTickEvent.Post event) {
        Simulator.simulateWorld();
        Simulator.healWhenSleeping();
    }

}
