package me.croshaw.yop.item.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import me.croshaw.yop.Yop;
import me.croshaw.yop.item.PocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class PocketItemEntry extends LeafEntry {
    private final PocketItem item;
    private final int min;
    private final int max;
    protected PocketItemEntry(PocketItem item, int min, int max, int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
        super(weight, quality, conditions, functions);
        this.item = item;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
        ItemStack stack = item.getDefaultStack();
        PocketItem.setupStack(stack, context.getRandom(), min, max);
        lootConsumer.accept(stack);
    }

    public static LeafEntry.Builder<?> builder(PocketItem item, int min, int max) {
        return builder((weight, quality, conditions, functions) -> new PocketItemEntry(item, min, max, weight, quality, conditions, functions));
    }

    @Override
    public LootPoolEntryType getType() {
        return Yop.POCKET_ITEM_ENTRY;
    }

    public static class Serializer extends LeafEntry.Serializer<PocketItemEntry> {

        @Override
        public void addEntryFields(JsonObject jsonObject, PocketItemEntry pocketItemEntry, JsonSerializationContext jsonSerializationContext) {
            super.addEntryFields(jsonObject, pocketItemEntry, jsonSerializationContext);
            Identifier identifier = Registry.ITEM.getId(pocketItemEntry.item);
            if (identifier == null) {
                throw new IllegalArgumentException("Can't serialize unknown item " + pocketItemEntry.item);
            } else {
                jsonObject.addProperty("name", identifier.toString());
                jsonObject.addProperty("min", pocketItemEntry.min);
                jsonObject.addProperty("max", pocketItemEntry.max);
            }

        }

        @Override
        protected PocketItemEntry fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int i, int j, LootCondition[] lootConditions, LootFunction[] lootFunctions) {
            final Item item = JsonHelper.getItem(jsonObject, "name");
            final int mix = JsonHelper.getInt(jsonObject, "min", 3);
            final int max = JsonHelper.getInt(jsonObject, "max");
            return new PocketItemEntry((PocketItem) item, mix, max, i, j, lootConditions, lootFunctions);
        }
    }
}
