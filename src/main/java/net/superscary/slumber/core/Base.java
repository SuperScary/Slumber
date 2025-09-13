package net.superscary.slumber.core;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.superscary.slumber.hooks.PlayerSleepHook;

public abstract class Base implements Slumber {

    static Slumber INSTANCE;

    public Base(ModContainer container, IEventBus modEventBus) {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }

        INSTANCE = this;

        // Register global gameplay hooks
        SlumberGameRules.ensureRegistered();
        NeoForge.EVENT_BUS.register(new PlayerSleepHook());
    }

    @Override
    public Collection<ServerPlayer> getPlayers() {
        var server = getCurrentServer();
        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
