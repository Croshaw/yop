package me.croshaw.yop.registry;

import me.croshaw.yop.Yop;
import me.croshaw.yop.item.LockedItem;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.item.material.PocketArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.registry.Registry;

public class ItemsRegistry {
    public static final LockedItem LOCKED_ITEM = new LockedItem();
    public static final ArmorMaterial POCKET_ARMOR_MATERIAL = new PocketArmorMaterial();
    public static final PocketItem POCKETS_BELT = new PocketItem(EquipmentSlot.LEGS);
    public static final PocketItem POCKETS_BACKPACK = new PocketItem(EquipmentSlot.CHEST);

    public static void registry() {
        Registry.register(Registry.ITEM, Yop.id("locked_item"), ItemsRegistry.LOCKED_ITEM);
        Registry.register(Registry.ITEM, Yop.id("pockets_belt"), ItemsRegistry.POCKETS_BELT);
        Registry.register(Registry.ITEM, Yop.id("pockets_backpack"), ItemsRegistry.POCKETS_BACKPACK);
    }
}
