package me.croshaw.yop.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.croshaw.yop.Yop;
import me.croshaw.yop.registry.ItemsRegistry;
import me.croshaw.yop.utils.ContainerHelper;
import me.croshaw.yop.utils.PocketComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PocketItem extends DyeableArmorItem {
    public PocketItem(EquipmentSlot slot) {
        super(ItemsRegistry.POCKET_ARMOR_MATERIAL, slot, new Settings().group(ItemGroup.TOOLS));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient && !PocketComponent.isSetup(stack))
            setupStack(stack, world.getRandom());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(PocketComponent.isSetup(stack)) {
            int invSize = PocketComponent.getInventorySize(stack);
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable((invSize < 5 ? "item.yop.pockets_item.tooltip_1" : "item.yop.pockets_item.tooltip_2"),invSize).formatted(Formatting.BLUE));
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return ImmutableMultimap.of();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return ImmutableMultimap.of();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public void equipEvent(LivingEntity livingEntity, ItemStack stack) {
        if(livingEntity instanceof ServerPlayerEntity player) {
            ContainerHelper.fixContainer(ContainerHelper.getHandler(player), player);
        }
    }

    public static void setupStack(ItemStack stack, Random random) {
        int minSize = stack.getItem().equals(ItemsRegistry.POCKETS_BACKPACK) ? Yop.CONFIG.InvMinBackpack : Yop.CONFIG.InvMinBelt;
        int maxSize = stack.getItem().equals(ItemsRegistry.POCKETS_BACKPACK) ? Yop.CONFIG.InvMaxBackpack : Yop.CONFIG.InvMaxBelt;
        setupStack(stack, random, minSize, maxSize);
    }

    public static void setupStack(ItemStack stack, Random random, int minSize, int maxSize) {
        PocketComponent.setInventorySize(stack, random.nextBetween(minSize, maxSize));
    }
}
