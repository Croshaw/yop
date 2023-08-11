package me.croshaw.yop.mixin;

import me.croshaw.yop.utils.ContainerHelper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "changeGameMode", at = @At("TAIL"))
    private void changeGameMode(GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity thys = (ServerPlayerEntity) (Object)this;
        ContainerHelper.fixContainer(ContainerHelper.getHandler(thys), thys);
    }

    @Inject(method = "openHandledScreen", at = @At(value = "TAIL"))
    private void onScreenHandlerOpened(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> cir) {
        ServerPlayerEntity thys = (ServerPlayerEntity) (Object)this;
        ContainerHelper.fixContainer(ContainerHelper.getHandler(thys), thys, thys.isCreative());
    }
}
