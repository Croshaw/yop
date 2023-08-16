package me.croshaw.yop.utils;

import net.minecraft.item.ItemStack;

public class PocketComponent {
    public static final String POCKET_KEY = "Inventory Size";

    public static boolean isSetup(ItemStack stack) {
        return stack.getOrCreateNbt().contains(POCKET_KEY);
    }

    public static int getInventorySize(ItemStack stack) {
        if(isSetup(stack))
            return stack.getOrCreateNbt().getInt(POCKET_KEY);
        setInventorySize(stack, 0);
        return 0;
    }

    public static void setInventorySize(ItemStack stack, int value) {
        stack.getOrCreateNbt().putInt(POCKET_KEY, value);
    }
}
