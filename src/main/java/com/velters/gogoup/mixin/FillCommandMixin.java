package com.velters.gogoup.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.velters.gogoup.HeightLimit;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(FillCommand.class)
public abstract class FillCommandMixin {
    @Inject(method = "fillBlocks", at = @At("HEAD"), cancellable = true)
    private static void gogoup$checkFillHeight(CommandSourceStack source, BoundingBox box, BlockInput input,
                                               @Coerce Object mode, Predicate<BlockInWorld> filter, boolean strict,
                                               CallbackInfoReturnable<Integer> cir)
            throws CommandSyntaxException {
        if (box.maxY() > HeightLimit.getMaxBuildY()) {
            throw new SimpleCommandExceptionType(Component.literal(
                    "GoGoUp: /fill の範囲が最大建築高度 Y=" + HeightLimit.getMaxBuildY() + " を超えています。"))
                    .create();
        }
    }
}
