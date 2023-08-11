package me.croshaw.yop.mixin;

import me.croshaw.yop.Yop;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreens.class)
public class HandledScreensMixin {
    @Inject(method = "open", at = @At("TAIL"))
    private static <T extends ScreenHandler> void open(@Nullable ScreenHandlerType<T> type, MinecraftClient client, int id, Text title, CallbackInfo ci) {
        ClientPlayNetworking.send(Yop.SYNC_CONTAINER_S2C, PacketByteBufs.create());
    }
}
