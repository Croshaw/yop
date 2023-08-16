package me.croshaw.yop.utils;

import dev.emi.trinkets.api.TrinketsApi;
import me.croshaw.extraslots.utils.InventoryHelper;
import me.croshaw.yop.Yop;
import me.croshaw.yop.client.YopClient;
import me.croshaw.yop.inventory.PocketSlot;
import me.croshaw.yop.item.PocketItem;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class ContainerHelper {

    public static ItemStack[] getEquipedTrinkets(PlayerEntity player, EquipmentSlot slot) {
        try {
            ItemStack[] itemStacks= new ItemStack[TrinketsApi.getPlayerSlots(player).size()];
            var equipped = TrinketsApi.getTrinketComponent(player).get().getEquipped(x -> x.getItem() instanceof PocketItem item && item.getSlotType().equals(slot));
            for (int i = 0; i< equipped.size();i++)
                itemStacks[i]=equipped.get(i).getRight();
            return itemStacks;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static int getPocketOffsetBySlot(PlayerEntity player, EquipmentSlot slot) {
        ItemStack equippedStack = player.getEquippedStack(slot);
        int size=0;
        if(!equippedStack.isEmpty() && equippedStack.getItem() instanceof PocketItem) {
            size = PocketComponent.getInventorySize(equippedStack);
        }
        if(Yop.IS_TRINKETS_LOADED) {
            ItemStack[] itemStacks = getEquipedTrinkets(player, slot);
            if(itemStacks!= null)
                for(int i=0;i<itemStacks.length;i++) {
                    if(itemStacks[i]==null) continue;
                    size=Math.max(PocketComponent.getInventorySize(itemStacks[i]), size);
                    break;
                }
        }
        return size;
    }

    public static int getTotalPocketOffset(PlayerEntity player) {
        return getPocketOffsetBySlot(player, EquipmentSlot.LEGS) + getPocketOffsetBySlot(player, EquipmentSlot.CHEST);
    }

    public static ScreenHandler getHandler(PlayerEntity player) {
        return player.currentScreenHandler == null ? player.playerScreenHandler : player.currentScreenHandler;
    }

    public static void fixContainer(ScreenHandler screenHandler, ServerPlayerEntity player) {
        int offset = getTotalPocketOffset(player);
        if(offset-27 == InventoryHelper.getSize(player))
            return;
        if(offset > 27) {
            if(!Yop.IS_EXTRA_SLOTS_LOADED)
                offset=27;
            else
                InventoryHelper.resizeInventory(player, offset-27);
        } else if(InventoryHelper.getSize(player)>0)
                InventoryHelper.resizeInventory(player, 0);
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<Boolean> enables = new ArrayList<>();
        for (int i = 0; i < screenHandler.slots.size(); i++) {
            Slot slot = screenHandler.slots.get(i);
            if(shouldBeSetup(slot, player)) {
                boolean shouldHidden = !player.isCreative() && shouldBeRemoved(slot, player, offset);
                indexes.add(i);
                enables.add(!shouldHidden);
                if (slot instanceof PocketSlot pSlot) {
                    pSlot.setEnable(!shouldHidden, player);
                } else {
                    screenHandler.slots.set(i, new PocketSlot(slot.inventory, slot.getIndex(), slot.x, slot.y, player, !shouldHidden));
                }
                screenHandler.slots.get(i).id = i;
            }
        }
        sendToClient(player, indexes, enables);
    }

    public static void sendToClient(ServerPlayerEntity player, ArrayList<Integer> indexes, ArrayList<Boolean> enables) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIntArray(indexes.stream().mapToInt(x->x).toArray());
        buf.writeInt(ConvertUtils.getIntMask(enables));
        ServerPlayNetworking.send(player, YopClient.SYNC_CONTAINER_S2C, buf);
    }

    public static boolean shouldBeSetup(Slot slot, PlayerEntity player) {
        return shouldBeRemoved(slot, player, 0);
    }

    public static boolean shouldBeRemoved(Slot slot, PlayerEntity player, int offset) {
        return slot.inventory == player.getInventory() && offset < 27 && shouldBeRemoved(slot.getIndex(), offset);
    }

    public static boolean shouldBeRemoved(int slotId, int offset) {
        return slotId >=9 + offset && slotId<36;
    }
}
