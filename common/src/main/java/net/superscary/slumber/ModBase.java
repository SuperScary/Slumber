package net.superscary.slumber;

import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public abstract class ModBase implements Slumber {

    static Slumber INSTANCE;

    public ModBase() {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }

        INSTANCE = this;
    }

    @Override
    public Collection<ServerPlayer> getPlayers() {
        var server = getCurrentServer();
        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

}
