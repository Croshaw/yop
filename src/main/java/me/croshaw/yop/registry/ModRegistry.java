package me.croshaw.yop.registry;

import me.croshaw.yop.Yop;
import me.croshaw.yop.item.PocketItem;
import me.croshaw.yop.utils.ContainerHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModRegistry {

    public static int getCost(int current) {
        return current>64 && !Yop.IS_COINIFY_LOADED ? 64 : current;
    }
    public static void registryTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LEATHERWORKER, 2,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, getCost(Yop.CONFIG.CostBelt)),
                            new ItemStack(ItemsRegistry.POCKETS_BELT, 1),
                            6,1,0.0f
                    )));
                }
        );
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LEATHERWORKER, 3,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, getCost(Yop.CONFIG.CostBackpack)),
                            new ItemStack(ItemsRegistry.POCKETS_BACKPACK, 1),
                            6,1,0.0f
                    )));
                }
        );
    }

    public static void registry() {
        ItemsRegistry.registry();
        LootTablesRegistry.registry();
        registryTrades();
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previousStack, currentStack) -> {
            if(livingEntity instanceof ServerPlayerEntity player)
            if(currentStack.getItem() instanceof PocketItem || previousStack.getItem() instanceof PocketItem)
                ContainerHelper.fixContainer(ContainerHelper.getHandler(player), player);
        });
    }
}
