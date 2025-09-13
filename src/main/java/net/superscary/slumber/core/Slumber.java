package net.superscary.slumber.core;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public interface Slumber {

    String NAME = "Slumber";
    String MODID = "slumber";

    static Logger LOGGER = LoggerFactory.getLogger(NAME);

    static Slumber instance() {
        return Base.INSTANCE;
    }

    Collection<ServerPlayer> getPlayers();

    Level getClientLevel();

    MinecraftServer getCurrentServer();

}
