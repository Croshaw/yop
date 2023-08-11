package me.croshaw.yop.client;

import me.croshaw.yop.Yop;
import me.croshaw.yop.client.renderer.BackpackArmorRenderer;
import me.croshaw.yop.client.renderer.BeltArmorRenderer;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.registry.ItemsRegistry;
import me.croshaw.yop.utils.ContainerHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.Identifier;

public class YopClient implements ClientModInitializer {
    public static final Identifier SYNC_CONTAINER_S2C = Yop.id("sync_container_s2c");

    public static final BackpackArmorRenderer BACKPACK_ARMOR_RENDERER = new BackpackArmorRenderer();
    public static final BeltArmorRenderer BELT_ARMOR_RENDERER = new BeltArmorRenderer();
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0? ((PocketItem) stack.getItem()).getColor(stack) : 0xFFFFFFF, ItemsRegistry.POCKETS_BACKPACK, ItemsRegistry.POCKETS_BELT);
        ArmorRenderer.register(BACKPACK_ARMOR_RENDERER, ItemsRegistry.POCKETS_BACKPACK);
        ArmorRenderer.register(BELT_ARMOR_RENDERER, ItemsRegistry.POCKETS_BELT);

        ClientPlayNetworking.registerGlobalReceiver(SYNC_CONTAINER_S2C, (client, handler, buf, responseSender) -> {
            if(client.player != null)
                ContainerHelper.fixContainer(ContainerHelper.getHandler(client.player), client.player, buf.readBoolean());
        });
    }
}
