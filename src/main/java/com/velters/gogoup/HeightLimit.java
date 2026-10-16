package com.velters.gogoup;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class HeightLimit {
    public static final int MIN_BUILD_Y = 0;
    public static final int MAX_BUILD_Y = 2031;

    private static volatile int maxBuildY = MAX_BUILD_Y;

    private HeightLimit() {
    }

    public static int getMaxBuildY() {
        return maxBuildY;
    }

    static void setMaxBuildY(int value) {
        maxBuildY = value;
    }

    public static boolean isAboveLimit(BlockPos pos) {
        return pos.getY() > getMaxBuildY();
    }

    public static void sendLimitMessage(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(
                    Component.literal("GoGoUp: 最大建築高度は Y=" + getMaxBuildY() + " です。"), true);
        }
    }
}
