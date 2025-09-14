package net.superscary.slumber.neoforge.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.superscary.slumber.CommonClass;
import net.superscary.slumber.ModBase;
import net.superscary.slumber.SlumberGameRules;
import net.superscary.slumber.neoforge.hooks.PlayerSleepHook;
import org.jetbrains.annotations.Nullable;

public abstract class SlumberNeoForgeBase extends ModBase {

    public SlumberNeoForgeBase(ModContainer container, IEventBus eventBus) {
        super();
        CommonClass.init();

        SlumberGameRules.SLUMBER_TICK_MULTIPLIER = GameRules
                .register("slumberTickMultiplier", GameRules.Category.UPDATES, GameRules.IntegerValue.create(20));

        NeoForge.EVENT_BUS.register(new PlayerSleepHook());
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}