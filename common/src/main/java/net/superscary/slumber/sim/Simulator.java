package net.superscary.slumber.sim;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.superscary.slumber.Slumber;
import net.superscary.slumber.SlumberGameRules;

public class Simulator {

    private static boolean sleepingActive = false;
    private static int originalSleepingPercent = -1; // cached from the overworld when entering sleep mode

    /**
     * Simulate extra world ticks when all players are sleeping, based on the
     * SLUMBER_TICK_MULTIPLIER game rule.
     */
    public static void simulateWorld() {
        final MinecraftServer server = Slumber.instance().getCurrentServer();
        if (server == null)
            return;

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

        if (!sleepingActive)
            return;

        // Determine multiplier from game rule. Minimum 1 (no acceleration).
        int multiplier = Math.max(1,
                server.overworld().getGameRules().getInt(SlumberGameRules.SLUMBER_TICK_MULTIPLIER));
        int extraTicks = multiplier - 1;

        // Run extra full world ticks so that blocks, entities, schedulers, etc. all
        // progress.
        for (int i = 0; i < extraTicks; i++) {
            for (ServerLevel level : server.getAllLevels()) {
                level.tick(() -> true);
            }
        }

        // If it has become day in the overworld, allow vanilla to wake players by
        // restoring the threshold.
        if (server.overworld().getGameTime() >= server.overworld().getDayTime() && originalSleepingPercent >= 0) {
            setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
            originalSleepingPercent = -1;
        }
    }

    /**
     * Heal sleeping players by 1 health point (half a heart) each tick when all
     * players are sleeping, if the HEAL_WHEN_SLEEPING game rule is enabled.
     *
     * * We should calculate healing time based on the player's actual sleep time and the time
     * * it takes to heal naturally, this just adds 1 health per tick.
     */
    @Deprecated(since = "1.0.1+1.21.1")
    public static void healWhenSleeping() {
        final MinecraftServer server = Slumber.instance().getCurrentServer();
        if (server == null)
            return;

        final boolean allSleeping = areAllNonSpectatorsSleeping(server);

        if (!allSleeping)
            return;

        boolean healWhenSleeping = server.overworld().getGameRules().getBoolean(SlumberGameRules.HEAL_WHEN_SLEEPING);
        boolean hungerWhenHealing = server.overworld().getGameRules().getBoolean(SlumberGameRules.HUNGER_WHEN_HEALING);
        if (!healWhenSleeping)
            return;

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.isSleeping()) {
                player.heal(1.0F);
                // TODO: configurable hunger cost.
                // ! This is experimental.
                if (hungerWhenHealing) player.causeFoodExhaustion(0.005F);
            }
        }
    }

    private static boolean areAllNonSpectatorsSleeping(MinecraftServer server) {
        var players = Slumber.instance().getPlayers();
        int active = 0;
        for (ServerPlayer p : players) {
            if (!p.isSpectator()) {
                active++;
                if (!p.isSleeping())
                    return false;
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
