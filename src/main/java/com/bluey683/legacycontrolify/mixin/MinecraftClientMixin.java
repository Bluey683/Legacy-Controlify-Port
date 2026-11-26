package com.bluey683.legacycontrolify.mixin;

import com.bluey683.legacycontrolify.ControlifyConfigScreen;
import com.bluey683.legacycontrolify.GamepadManager;
import com.bluey683.legacycontrolify.VirtualKeyboardScreen;
import com.bluey683.legacycontrolify.VirtualMouse;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    private boolean legacyControlify$f8WasDown;
    private boolean legacyControlify$f7WasDown;
    private boolean legacyControlify$f6WasDown;
    private boolean legacyControlify$f5WasDown;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void legacyControlify$pollGamepad(CallbackInfo ci) {
        GamepadManager.poll();

        MinecraftClient client = (MinecraftClient) (Object) this;
        VirtualMouse.tick(client);

        boolean f8Down = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F8) == GLFW.GLFW_PRESS;
        if (f8Down && !legacyControlify$f8WasDown) {
            ControlifyConfigScreen.open();
        }
        legacyControlify$f8WasDown = f8Down;

        boolean f7Down = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F7) == GLFW.GLFW_PRESS;
        if (f7Down && !legacyControlify$f7WasDown && client.currentScreen == null) {
            client.openScreen(new com.bluey683.legacycontrolify.RadialMenuScreen());
        }
        legacyControlify$f7WasDown = f7Down;

        boolean f6Down = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F6) == GLFW.GLFW_PRESS;
        if (f6Down && !legacyControlify$f6WasDown) {
            client.openScreen(new VirtualKeyboardScreen(client.currentScreen));
        }
        legacyControlify$f6WasDown = f6Down;

        boolean f5Down = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F5) == GLFW.GLFW_PRESS;
        if (f5Down && !legacyControlify$f5WasDown) VirtualMouse.toggle();
        legacyControlify$f5WasDown = f5Down;
    }
}
