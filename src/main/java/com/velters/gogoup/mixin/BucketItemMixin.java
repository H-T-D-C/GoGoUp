package com.velters.gogoup.mixin;

import com.velters.gogoup.HeightLimit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Inject(method = "emptyContents", at = @At("HEAD"), cancellable = true)
    private void gogoup$checkBucketPlacementHeight(LivingEntity user, Level level, BlockPos pos,
                                                  BlockHitResult hitResult,
                                                  CallbackInfoReturnable<Boolean> cir) {
        if (user instanceof ServerPlayer player && level instanceof ServerLevel
                && HeightLimit.isAboveLimit(pos)) {
            HeightLimit.sendLimitMessage(player);
            cir.setReturnValue(false);
        }
    }
}
