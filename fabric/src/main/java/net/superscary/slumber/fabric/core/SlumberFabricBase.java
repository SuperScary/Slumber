package net.superscary.slumber.fabric.core;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.superscary.slumber.CommonClass;
import net.superscary.slumber.ModBase;
import net.superscary.slumber.SlumberGameRules;
import net.superscary.slumber.sim.Simulator;
import org.jetbrains.annotations.Nullable;

public abstract class SlumberFabricBase extends ModBase {

    private volatile MinecraftServer currentServer;

    public SlumberFabricBase() {
        super();
        CommonClass.init();

        // Register game rules used by common logic
        SlumberGameRules.SLUMBER_TICK_MULTIPLIER = GameRules.register(
                "slumberTickMultiplier",
                GameRules.Category.UPDATES,
                GameRules.IntegerValue.create(20)
        );
        SlumberGameRules.HEAL_WHEN_SLEEPING = GameRules.register(
                "slumberHealWhenSleeping",
                GameRules.Category.PLAYER,
                GameRules.BooleanValue.create(false)
        );
        SlumberGameRules.HUNGER_WHEN_HEALING = GameRules.register(
                "slumberHungerWhenHealing",
                GameRules.Category.PLAYER,
                GameRules.BooleanValue.create(false)
        );

        // Track current server lifecycle for common access
        ServerLifecycleEvents.SERVER_STARTED.register(server -> this.currentServer = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.currentServer = null);

        // Run simulation each server tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            Simulator.simulateWorld();
            Simulator.healWhenSleeping();
        });
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return currentServer;
    }
}

