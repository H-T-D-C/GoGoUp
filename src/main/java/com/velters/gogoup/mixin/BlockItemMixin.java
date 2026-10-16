package com.velters.gogoup.mixin;

import com.velters.gogoup.HeightLimit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void gogoup$checkPlayerPlacementHeight(BlockPlaceContext context,
                                                  CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getLevel() instanceof ServerLevel && HeightLimit.isAboveLimit(context.getClickedPos())) {
            HeightLimit.sendLimitMessage(context.getPlayer());
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
