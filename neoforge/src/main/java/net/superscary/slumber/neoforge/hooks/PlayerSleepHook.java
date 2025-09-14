package net.superscary.slumber.neoforge.hooks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.superscary.slumber.sim.Simulator;

public class PlayerSleepHook {

    @SubscribeEvent
    public void onServerTick(final ServerTickEvent.Post event) {
        Simulator.simulateWorld();
        Simulator.healWhenSleeping();
    }

}
