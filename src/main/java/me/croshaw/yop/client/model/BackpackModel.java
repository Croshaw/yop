package me.croshaw.yop.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class BackpackModel extends BipedEntityModel<LivingEntity> {

    public BackpackModel() {
        super(createTexturedModelData().createModel());
        this.setVisible(false);
        this.body.visible = true;
    }

    public static TexturedModelData createTexturedModelData() {
        ModelData modelData = getModelData(Dilation.NONE, 0.0F);
        ModelPartData root = modelData.getRoot().getChild("body");

        root.addChild("base", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-3.5F, .5f, 2.2F, 7.0F, 10.0F, 4.0F),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }
}
