package me.croshaw.yop.item;

import me.croshaw.yop.registry.ItemsRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class PocketItem extends DyeableArmorItem {
    public PocketItem(EquipmentSlot slot) {
        super(ItemsRegistry.POCKET_ARMOR_MATERIAL, slot, new Settings().group(ItemGroup.TOOLS));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
