package com.bluey683.legacycontrolify.mixin;

import com.bluey683.legacycontrolify.VirtualMouse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenVirtualMouseMixin {
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("TAIL"))
    private void legacyControlify$drawVirtualPointer(MatrixStack matrices, int mouseX, int mouseY,
                                                       float delta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (VirtualMouse.isVisible(client)) {
            int x = VirtualMouse.getX();
            int y = VirtualMouse.getY();
            DrawableHelper.fill(matrices, x - 5, y - 1, x + 6, y + 2, 0xFFFFFFFF);
            DrawableHelper.fill(matrices, x - 1, y - 5, x + 2, y + 6, 0xFFFFFFFF);
        }
    }
}
