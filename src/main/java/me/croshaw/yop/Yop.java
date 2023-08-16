package me.croshaw.yop;

import draylar.omegaconfig.OmegaConfig;
import me.croshaw.yop.config.YopConfig;
import me.croshaw.yop.item.entry.PocketItemEntry;
import me.croshaw.yop.registry.ModRegistry;
import me.croshaw.yop.utils.ContainerHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.util.Identifier;

public class Yop implements ModInitializer {
    public static final String MOD_ID = "yop";
    public static final Identifier SYNC_CONTAINER_S2C = id("sync_container_s2c");
    public static final LootPoolEntryType POCKET_ITEM_ENTRY = new LootPoolEntryType(new PocketItemEntry.Serializer());
    public static final boolean IS_EXTRA_SLOTS_LOADED = FabricLoader.getInstance().isModLoaded("extraslots");
    public static final boolean IS_COINIFY_LOADED = FabricLoader.getInstance().isModLoaded("conify");
    public static final boolean IS_TRINKETS_LOADED = FabricLoader.getInstance().isModLoaded("trinkets");
    public static YopConfig CONFIG = OmegaConfig.register(YopConfig.class);

    @Override
    public void onInitialize() {
        ModRegistry.registry();
        ServerPlayNetworking.registerGlobalReceiver(SYNC_CONTAINER_S2C, (server, player, handler, buf, responseSender) -> {
            ContainerHelper.fixContainer(ContainerHelper.getHandler(player), player);
        });
        CONFIG.save();
    }
    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
