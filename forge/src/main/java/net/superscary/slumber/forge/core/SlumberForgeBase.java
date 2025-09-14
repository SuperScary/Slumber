package net.superscary.slumber.forge.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.superscary.slumber.CommonClass;
import net.superscary.slumber.ModBase;
import net.superscary.slumber.SlumberGameRules;
import net.superscary.slumber.forge.hooks.PlayerSleepHook;
import org.jetbrains.annotations.Nullable;

public abstract class SlumberForgeBase extends ModBase {

    public SlumberForgeBase() {
        super();
        CommonClass.init();

        SlumberGameRules.SLUMBER_TICK_MULTIPLIER = GameRules
                .register("slumberTickMultiplier", GameRules.Category.UPDATES, GameRules.IntegerValue.create(20));
        SlumberGameRules.HEAL_WHEN_SLEEPING = GameRules
                .register("slumberHealWhenSleeping", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
        SlumberGameRules.HUNGER_WHEN_HEALING = GameRules
                .register("slumberHungerWhenHealing", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

        MinecraftForge.EVENT_BUS.register(new PlayerSleepHook());
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}