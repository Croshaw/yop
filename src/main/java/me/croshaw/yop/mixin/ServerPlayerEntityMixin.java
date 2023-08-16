package me.croshaw.yop.mixin;

import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import me.croshaw.yop.Yop;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.utils.ContainerHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Unique
    private final Map<String, ItemStack> lastEquippedTrinkets = new HashMap<>();
    @Inject(method = "changeGameMode", at = @At("TAIL"))
    private void changeGameMode(GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayerEntity thys = (ServerPlayerEntity) (Object)this;
        ContainerHelper.fixContainer(ContainerHelper.getHandler(thys), thys);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if(!Yop.IS_TRINKETS_LOADED) return;
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        TrinketsApi.getTrinketComponent(player).ifPresent(trinkets -> {
            Map<String, ItemStack> newlyEquippedTrinkets = new HashMap<>();
            trinkets.forEach((ref, stack) -> {
                TrinketInventory inventory = ref.inventory();
                SlotType slotType = inventory.getSlotType();
                int index = ref.index();
                ItemStack oldStack = getOldStack(slotType, index);
                ItemStack newStack = inventory.getStack(index);
                ItemStack copy = newStack.copy();
                String newRef = slotType.getGroup() + "/" + slotType.getName() + "/" + index;
                newlyEquippedTrinkets.put(newRef, copy);

                if (!ItemStack.areEqual(newStack, oldStack)) {
                    if (newStack.getItem() instanceof PocketItem || oldStack.getItem() instanceof PocketItem) {
                        PocketItem item = (PocketItem) (newStack.getItem() instanceof PocketItem ? newStack.getItem() : oldStack.getItem());
                        ServerEntityEvents.EQUIPMENT_CHANGE.invoker().onChange(player, item.getSlotType(), oldStack, newStack);
                    }
                }
            });
            lastEquippedTrinkets.clear();
            lastEquippedTrinkets.putAll(newlyEquippedTrinkets);
        });
    }
    @Unique
    private ItemStack getOldStack(SlotType type, int index) {
        return lastEquippedTrinkets.getOrDefault(type.getGroup() + "/" + type.getName() + "/" + index, ItemStack.EMPTY);
    }
}
