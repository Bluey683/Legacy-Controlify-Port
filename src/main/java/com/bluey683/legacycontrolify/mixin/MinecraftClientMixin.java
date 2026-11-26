package com.bluey683.legacycontrolify.mixin;

import com.bluey683.legacycontrolify.ControlifyConfigScreen;
import com.bluey683.legacycontrolify.GamepadManager;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    private boolean legacyControlify$f8WasDown;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void legacyControlify$pollGamepad(CallbackInfo ci) {
        GamepadManager.poll();

        MinecraftClient client = (MinecraftClient) (Object) this;
        boolean f8Down = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F8) == GLFW.GLFW_PRESS;
        if (f8Down && !legacyControlify$f8WasDown) {
            ControlifyConfigScreen.open();
        }
        legacyControlify$f8WasDown = f8Down;
    }
}
