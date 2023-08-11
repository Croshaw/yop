package me.croshaw.yop.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class BeltModel extends BipedEntityModel<LivingEntity> {

    public BeltModel() {
        super(createTexturedModelData().createModel());
        this.setVisible(false);
        this.body.visible = true;
    }

    public static TexturedModelData createTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        ModelPartData body = root.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 10.0F, -3.0F, 10.0F, 2.0F, 6.0F, new Dilation(0.0F))
                        .uv(0, 8).cuboid(-4.0F, 11.0F, -4.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
                        .uv(10, 8).cuboid(1.0F, 11.0F, -4.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.rotation(0.0F, 0.0F, 0.0F));
        body.addChild("pouch",
                ModelPartBuilder.create().uv(18, 8).cuboid(0.0F, -13.0F, -6.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }
}
