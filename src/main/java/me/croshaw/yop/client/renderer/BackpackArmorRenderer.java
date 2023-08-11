package me.croshaw.yop.client.renderer;

import me.croshaw.yop.Yop;
import me.croshaw.yop.client.model.BackpackModel;
import me.croshaw.yop.item.PocketItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BackpackArmorRenderer implements ArmorRenderer {
    public static final Identifier BACKPACK_MODEL_TEXTURE = Yop.id("textures/armor/pockets_backpack.png");
    public static final Identifier BACKPACK_MODEL_OVERLAY_TEXTURE = Yop.id("textures/armor/pockets_backpack.png");
    private BackpackModel model;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        BackpackModel model = getModel();
        contextModel.setAttributes(model);

        int i = ((PocketItem) stack.getItem()).getColor(stack);
        float r = (float) (i >> 16 & 255) / 255.0F;
        float g = (float) (i >> 8 & 255) / 255.0F;
        float b = (float) (i & 255) / 255.0F;

        model.setAngles(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(BACKPACK_MODEL_TEXTURE), false, stack.hasGlint());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1);
    }

    public BackpackModel getModel() {
        if (model == null) {
            model = new BackpackModel();
        }
        return model;
    }
}