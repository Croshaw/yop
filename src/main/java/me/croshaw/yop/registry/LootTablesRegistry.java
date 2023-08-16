package me.croshaw.yop.registry;

import me.croshaw.yop.item.entry.PocketItemEntry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTablesRegistry {
    private static final Identifier VILLAGE_TANNERY_ID = new Identifier("minecraft", "chests/village/village_tannery");
    private static final Identifier LEATHERWORKER_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/leatherworker_gift");
    private static final Identifier SIMPLE_DUNGEON_ID = new Identifier("minecraft", "chests/simple_dungeon");

    public static void registry() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(source.isBuiltin()) {
                if (LEATHERWORKER_GIFT_ID.equals(id)) {
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.5f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BACKPACK, 14, 18)));
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.5f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BELT, 6, 9)));
                } else if (SIMPLE_DUNGEON_ID.equals(id)) {
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.20f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BELT, 3, 6))
                            .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2f, 0.6f))));
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.10f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BACKPACK, 3, 14))
                            .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.2f, 0.75f))));
                } else if (VILLAGE_TANNERY_ID.equals(id)) {
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BELT, 3, 6)));
                    tableBuilder.pool(LootPool.builder()
                            .conditionally(RandomChanceLootCondition.builder(0.15f))
                            .with(PocketItemEntry.builder(ItemsRegistry.POCKETS_BACKPACK, 3, 14)));
                }
            }
        });
    }
}
