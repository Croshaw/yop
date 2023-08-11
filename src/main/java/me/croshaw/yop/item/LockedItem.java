package me.croshaw.yop.item;

import net.minecraft.item.Item;

public class LockedItem extends Item {
    public LockedItem() {
        super(new Settings().maxCount(1));
    }
}
