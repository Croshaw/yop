package me.croshaw.yop.mixin;

import me.croshaw.yop.registry.ItemsRegistry;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemEntity thys = ((ItemEntity) (Object) this);
        if(thys.getStack().isOf(ItemsRegistry.LOCKED_ITEM))
            thys.kill();
    }
}
