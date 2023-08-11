package me.croshaw.yop.utils;

import me.croshaw.yop.client.YopClient;
import me.croshaw.yop.inventory.PocketSlot;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

public class ContainerHelper {

    public static ScreenHandler getHandler(PlayerEntity player) {
        return player.currentScreenHandler == null ? player.playerScreenHandler : player.currentScreenHandler;
    }

    public static void fixContainer(ScreenHandler screenHandler, ServerPlayerEntity player) {
        fixContainer(screenHandler, (PlayerEntity) player, player.isCreative());
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(player.isCreative());
        ServerPlayNetworking.send(player, YopClient.SYNC_CONTAINER_S2C, buf);
    }

    public static void fixContainer(ScreenHandler screenHandler, PlayerEntity player, boolean isCreative) {
        for (int i = 0; i < screenHandler.slots.size(); i++) {
            Slot slot = screenHandler.slots.get(i);
            if(shouldBeSetup(slot, player)) {
                boolean shouldHidden = !isCreative && shouldBeRemoved(slot, player);
                if (slot instanceof PocketSlot pSlot) {
                    pSlot.setEnable(!shouldHidden, player);
                } else {
                    screenHandler.slots.set(i, new PocketSlot(slot.inventory, slot.getIndex(), slot.x, slot.y, player, !shouldHidden));
                }
                screenHandler.slots.get(i).id = i;
            }
        }
    }

    public static boolean shouldBeSetup(Slot slot, PlayerEntity player) {
        return slot.inventory==player.getInventory() && shouldBeSetup(slot.getIndex());
    }

    public static boolean shouldBeSetup(int slotId) {
        return slotId >=9 && slotId<36;
    }

    public static boolean shouldBeRemoved(Slot slot, PlayerEntity player) {
        return slot.inventory == player.getInventory() && shouldBeRemoved(slot.getIndex());
    }

    public static boolean shouldBeRemoved(int slotId) {
        return slotId >=9 && slotId<36;
    }
}
