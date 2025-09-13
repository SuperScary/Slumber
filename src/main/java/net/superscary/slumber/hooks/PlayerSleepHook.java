package net.superscary.slumber.hooks;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.superscary.slumber.core.Slumber;
import net.superscary.slumber.core.SlumberGameRules;

public class PlayerSleepHook {

    private boolean sleepingActive = false;
    private int originalSleepingPercent = -1; // cached from overworld when entering sleep mode

    @SubscribeEvent
    public void onServerTick(final ServerTickEvent.Post event) {
        final MinecraftServer server = Slumber.instance().getCurrentServer();
        if (server == null) return;

        final boolean allSleeping = areAllNonSpectatorsSleeping(server);

        if (allSleeping != sleepingActive) {
            sleepingActive = allSleeping;
            if (sleepingActive) {
                Slumber.LOGGER.info("[Slumber] All players sleeping — accelerating world ticks.");
                // Prevent vanilla night-skip by making the percentage unattainable
                originalSleepingPercent = getPlayersSleepingPercent(server.overworld());
                setPlayersSleepingPercentAllLevels(server, 101);
            } else {
                Slumber.LOGGER.info("[Slumber] Sleep ended — resuming normal tick rate.");
                // Restore vanilla night-skip threshold
                if (originalSleepingPercent >= 0) {
                    setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
                    originalSleepingPercent = -1;
                }
            }
        }

        if (!sleepingActive) return;

        // Determine multiplier from gamerule. Minimum 1 (no acceleration).
        int multiplier = Math.max(1, server.overworld().getGameRules().getInt(SlumberGameRules.SLUMBER_TICK_MULTIPLIER));
        int extraTicks = multiplier - 1;

        // Run extra full world ticks so that blocks, entities, schedulers, etc. all progress.
        for (int i = 0; i < extraTicks; i++) {
            for (ServerLevel level : server.getAllLevels()) {
                level.tick(() -> true);
            }
        }

        // If it has become day in the overworld, allow vanilla to wake players by restoring the threshold.
        if (server.overworld().getGameTime() >= server.overworld().getDayTime() && originalSleepingPercent >= 0) {
            setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
            originalSleepingPercent = -1;
        }
    }

    private static boolean areAllNonSpectatorsSleeping(MinecraftServer server) {
        var players = server.getPlayerList().getPlayers();
        int active = 0;
        for (ServerPlayer p : players) {
            if (!p.isSpectator()) {
                active++;
                if (!p.isSleeping()) return false;
            }
        }
        return active > 0;
    }

    private static int getPlayersSleepingPercent(ServerLevel level) {
        return level.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
    }

    private static void setPlayersSleepingPercentAllLevels(MinecraftServer server, int value) {
        for (ServerLevel level : server.getAllLevels()) {
            level.getGameRules().getRule(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE).set(value, server);
        }
    }
}
