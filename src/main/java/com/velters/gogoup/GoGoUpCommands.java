package com.velters.gogoup;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

import java.io.IOException;

public final class GoGoUpCommands {
    private GoGoUpCommands() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(Commands.literal("gogoup")
                        .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                        .executes(context -> showHeight(context.getSource()))
                        .then(Commands.literal("status")
                                .executes(context -> showHeight(context.getSource())))
                        .then(Commands.literal("height")
                                .executes(context -> showHeight(context.getSource()))
                                .then(Commands.argument("y", IntegerArgumentType.integer(
                                                HeightLimit.MIN_BUILD_Y, HeightLimit.MAX_BUILD_Y))
                                        .executes(context -> setHeight(context.getSource(),
                                                IntegerArgumentType.getInteger(context, "y")))))
                        .then(Commands.literal("reload")
                                .executes(context -> reload(context.getSource())))));
    }

    private static int showHeight(CommandSourceStack source) {
        source.sendSuccess(
                () -> Component.literal("現在の最大建築高度: Y=" + HeightLimit.getMaxBuildY()
                        + " (設定範囲: " + HeightLimit.MIN_BUILD_Y + "〜"
                        + HeightLimit.MAX_BUILD_Y + ")"), false);
        return 1;
    }

    private static int setHeight(CommandSourceStack source, int value) {
        try {
            GoGoUpConfig.setMaxBuildY(value);
        } catch (IOException exception) {
            source.sendFailure(Component.literal(
                    "設定を保存できませんでした。上限は変更されていません: " + exception.getMessage()));
            return 0;
        }

        source.sendSuccess(
                () -> Component.literal("最大建築高度を Y=" + value
                        + " に変更しました。"), true);
        return 1;
    }

    private static int reload(CommandSourceStack source) {
        try {
            GoGoUpConfig.reload();
        } catch (IOException exception) {
            source.sendFailure(Component.literal(
                    "設定を再読込できませんでした。現在の上限を維持します: " + exception.getMessage()));
            return 0;
        }

        int value = HeightLimit.getMaxBuildY();
        source.sendSuccess(
                () -> Component.literal("設定を再読込みしました。最大建築高度: Y=" + value), true);
        return 1;
    }
}
