package net.superscary.slumber;

import net.minecraft.world.level.GameRules;

public class SlumberGameRules {

    private SlumberGameRules() {
    }

    // Custom game rule: total tick multiplier while all players sleep (min 1)
    public static GameRules.Key<GameRules.IntegerValue> SLUMBER_TICK_MULTIPLIER;

    // Touch to force static init (and thus registration) when called from mod
    // bootstrap
    public static void ensureRegistered() {
        /* no-op */
    }
}
