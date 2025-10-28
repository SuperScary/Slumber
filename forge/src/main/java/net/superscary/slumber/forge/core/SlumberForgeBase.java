package net.superscary.slumber.forge.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.superscary.slumber.CommonClass;
import net.superscary.slumber.ModBase;
import net.superscary.slumber.SlumberGameRules;
import org.jetbrains.annotations.Nullable;

public abstract class SlumberForgeBase extends ModBase {

    public SlumberForgeBase() {
        super();
        CommonClass.init();

        SlumberGameRules.SLUMBER_TICK_MULTIPLIER = GameRules.register("slumberTickMultiplier", GameRules.Category.UPDATES, GameRules.IntegerValue.create(20));

    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}