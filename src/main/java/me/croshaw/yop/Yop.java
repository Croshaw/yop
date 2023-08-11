package me.croshaw.yop;

import me.croshaw.yop.client.YopClient;
import me.croshaw.yop.registry.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class Yop implements ModInitializer {
    public static final String MOD_ID = "yop";
    public static final Identifier SYNC_CONTAINER_S2C = id("sync_container_s2c");

    @Override
    public void onInitialize() {
        ModRegistry.registry();
        ServerPlayNetworking.registerGlobalReceiver(SYNC_CONTAINER_S2C, (server, player, handler, buf, responseSender) -> {
            PacketByteBuf buf1 = PacketByteBufs.create();
            buf1.writeBoolean(player.isCreative());
            ServerPlayNetworking.send(player, YopClient.SYNC_CONTAINER_S2C, buf1);
        });
    }
    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
