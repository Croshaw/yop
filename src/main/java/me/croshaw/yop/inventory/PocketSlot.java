package me.croshaw.yop.inventory;

import me.croshaw.yop.registry.ItemsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class PocketSlot extends Slot {
    private boolean isEnable;

    public PocketSlot(Inventory inventory, int index, int x, int y, PlayerEntity player) {
        this(inventory, index, x, y, player, false);
    }

    public PocketSlot(Inventory inventory, int index, int x, int y, PlayerEntity player, boolean isEnable) {
        super(inventory, index, x, y);
        setEnable(isEnable, player);
    }

    public void setEnable(boolean value, PlayerEntity player) {
        this.isEnable = value;
        if(isEnable) {
            if(getStack().isOf(ItemsRegistry.LOCKED_ITEM))
                setStack(ItemStack.EMPTY);
        }
        else {
            if(getStack().isOf(ItemsRegistry.LOCKED_ITEM))
                return;
            if(!getStack().isEmpty() && player != null)
                player.dropStack(getStack());
            setStack(ItemsRegistry.LOCKED_ITEM.getDefaultStack());
        }
    }

    @Override
    public int getMaxItemCount() {
        return isEnable ? super.getMaxItemCount() : 0;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return isEnable;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isEnable;
    }
}
