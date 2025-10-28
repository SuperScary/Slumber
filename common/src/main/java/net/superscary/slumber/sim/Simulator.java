package net.superscary.slumber.sim;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.superscary.slumber.Slumber;
import net.superscary.slumber.SlumberGameRules;

public class Simulator {

    private static boolean sleepingActive = false;
    private static int originalSleepingPercent = -1;


    private static final int TARGET_SKIP_REAL_TICKS = 40; // ~2 seconds at 20 TPS

    /**
     * Simulate extra world ticks when all players are sleeping.
     * Speeds up time so the night ends in roughly TARGET_SKIP_REAL_TICKS
     * real server ticks (similar feel to vanilla), while still advancing
     * the world via full ticks.
     */
    public static void simulateWorld() {
        final MinecraftServer server = Slumber.instance().getCurrentServer();
        if (server == null)
            return;

        final boolean allSleeping = areAllNonSpectatorsSleeping();

        if (allSleeping != sleepingActive) {
            sleepingActive = allSleeping;
            if (sleepingActive) {
                Slumber.LOGGER.info("All players sleeping - accelerating world ticks.");

                originalSleepingPercent = getPlayersSleepingPercent(server.overworld());
                setPlayersSleepingPercentAllLevels(server, 101);
            } else {
                Slumber.LOGGER.info("Sleep ended - resuming normal tick rate.");

                if (originalSleepingPercent >= 0) {
                    setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
                    originalSleepingPercent = -1;
                }
            }
        }

        if (!sleepingActive)
            return;

        int remainingToMorning = ticksUntilNextMorning(server.overworld());
        if (remainingToMorning <= 0) {
            if (originalSleepingPercent >= 0) {
                setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
                originalSleepingPercent = -1;
            }
            return;
        }

        int ruleMultiplier = Math.max(1,
                server.overworld().getGameRules().getInt(SlumberGameRules.SLUMBER_TICK_MULTIPLIER));
        int floorExtra = ruleMultiplier - 1;
        int desiredExtra = (int) Math.ceil(remainingToMorning / (double) TARGET_SKIP_REAL_TICKS);
        int extraTicks = Math.max(floorExtra, desiredExtra);

        for (int i = 0; i < extraTicks; i++) {
            for (ServerLevel level : server.getAllLevels()) {
                level.tick(() -> true);
            }
        }

        if (ticksUntilNextMorning(server.overworld()) <= 0 && originalSleepingPercent >= 0) {
            setPlayersSleepingPercentAllLevels(server, originalSleepingPercent);
            originalSleepingPercent = -1;
        }
    }

    private static boolean areAllNonSpectatorsSleeping() {
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

    private static int ticksUntilNextMorning(ServerLevel level) {
        long dayTime = level.getDayTime() % 24000L;
        if (dayTime == 0L) return 0;
        return (int) (24000L - dayTime);
    }
}

