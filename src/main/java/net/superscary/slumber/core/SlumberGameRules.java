package net.superscary.slumber.core;

import net.minecraft.world.level.GameRules;

public final class SlumberGameRules {

    private SlumberGameRules() {}

    // Custom gamerule: total tick multiplier while all players sleep (min 1)
    public static final GameRules.Key<GameRules.IntegerValue> SLUMBER_TICK_MULTIPLIER =
            GameRules.register("slumberTickMultiplier", GameRules.Category.UPDATES, GameRules.IntegerValue.create(20));

    // Touch to force static init (and thus registration) when called from mod bootstrap
    public static void ensureRegistered() { /* no-op */ }
}

