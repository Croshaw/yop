package me.croshaw.yop.mixin;

import me.croshaw.yop.Yop;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.utils.ContainerHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(method = "damageArmor", at = @At(value = "TAIL"))
    private void damageArmor(DamageSource damageSource, float amount, int[] slots, CallbackInfo ci) {
        if(!Yop.IS_TRINKETS_LOADED) return;
        PlayerInventory thys = (PlayerInventory) (Object) this;
        for (int i : slots) {
            ItemStack[] itemStacks = ContainerHelper.getEquipedTrinkets(thys.player, EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, i));
            if(itemStacks==null) return;
            for (ItemStack itemStack : itemStacks) {
                if (itemStack != null && (!damageSource.isFire() || !itemStack.getItem().isFireproof()) && itemStack.getItem() instanceof PocketItem)
                    itemStack.damage((int) amount, thys.player, player -> player.sendEquipmentBreakStatus(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, i)));
            }
        }
    }
}
