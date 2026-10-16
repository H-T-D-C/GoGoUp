package com.velters.gogoup.mixin;

import com.velters.gogoup.HeightLimit;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockInput.class)
public abstract class BlockInputMixin {
    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void gogoup$checkPlacementHeight(ServerLevel level, BlockPos pos, int flags,
                                             CallbackInfoReturnable<Boolean> cir) {
        if (HeightLimit.isAboveLimit(pos)) {
            cir.setReturnValue(false);
        }
    }
}
