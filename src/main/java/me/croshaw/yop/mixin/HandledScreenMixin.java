package me.croshaw.yop.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.croshaw.yop.Yop;
import me.croshaw.yop.utils.ContainerHelper;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow public abstract T getScreenHandler();
    @Unique
    private static final Identifier LOCKED_SLOT_TEXTURE = Yop.id("textures/gui/locked_slot.png");
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0))
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(this.client == null || this.client.player == null || this.getScreenHandler() == null) return;
        int offset = ContainerHelper.getTotalPocketOffset(client.player);
        for(int i = 0; i < this.getScreenHandler().slots.size(); ++i) {
            Slot slot = this.getScreenHandler().slots.get(i);
            if(!this.client.player.isCreative() && ContainerHelper.shouldBeRemoved(slot, this.client.player, offset)) {
                this.setZOffset(100);
                this.itemRenderer.zOffset = 100.0F;
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderTexture(0, LOCKED_SLOT_TEXTURE);
                DrawableHelper.drawTexture(matrices, slot.x-1, slot.y-1, 0, 0, 18, 18, 18, 18);
                this.itemRenderer.zOffset = 0F;
                this.setZOffset(0);
            }
        }
    }
}
