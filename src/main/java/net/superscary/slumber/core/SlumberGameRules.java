package net.superscary.slumber.core;

import net.minecraft.world.level.GameRules;

public final class SlumberGameRules {

    private SlumberGameRules() {
    }

    // Custom gamerule: total tick multiplier while all players sleep (min 1)
    public static final GameRules.Key<GameRules.IntegerValue> SLUMBER_TICK_MULTIPLIER = GameRules
            .register("slumberTickMultiplier", GameRules.Category.UPDATES, GameRules.IntegerValue.create(20));
    public static final GameRules.Key<GameRules.BooleanValue> HEAL_WHEN_SLEEPING = GameRules
            .register("slumberHealWhenSleeping", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> HUNGER_WHEN_HEALING = GameRules
            .register("slumberHungerWhenHealing", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

    // Touch to force static init (and thus registration) when called from mod
    // bootstrap
    public static void ensureRegistered() {
        /* no-op */ }
}
