package me.croshaw.yop.client;

import me.croshaw.yop.Yop;
import me.croshaw.yop.client.renderer.BackpackArmorRenderer;
import me.croshaw.yop.client.renderer.BeltArmorRenderer;
import me.croshaw.yop.inventory.PocketSlot;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.registry.ItemsRegistry;
import me.croshaw.yop.utils.ContainerHelper;
import me.croshaw.yop.utils.ConvertUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
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
            if(client.player != null) {
                int[] indexes = buf.readIntArray();
                boolean[] enables = ConvertUtils.getBooleans(buf.readInt());
                ScreenHandler screenHandler = ContainerHelper.getHandler(client.player);
                for(int i = 0; i < indexes.length;i++) {
                    Slot slot = screenHandler.slots.get(indexes[i]);
                    if(slot instanceof PocketSlot pSlot)
                        pSlot.setEnable(enables[i], null);
                    else
                        screenHandler.slots.set(indexes[i], new PocketSlot(slot.inventory, slot.getIndex(), slot.x, slot.y, null, enables[i]));
                    screenHandler.slots.get(indexes[i]).id = indexes[i];
                }
            }
        });
    }
}
