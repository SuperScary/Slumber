package net.superscary.slumber;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface Slumber {

	String MOD_ID = "slumber";
	String MOD_NAME = "Slumber";
    Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    static Slumber instance() {
        return ModBase.INSTANCE;
    }

    Collection<ServerPlayer> getPlayers();

    Level getClientLevel();

    MinecraftServer getCurrentServer();


}