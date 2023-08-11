package me.croshaw.yop.mixin;

import me.croshaw.yop.utils.ContainerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "addPlayer", at = @At("TAIL"))
    private void addPlayer(ServerPlayerEntity player, CallbackInfo ci) {
        ContainerHelper.fixContainer(ContainerHelper.getHandler(player), (PlayerEntity)player, player.isCreative());
    }
}
