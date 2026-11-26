package com.bluey683.legacycontrolify.mixin;

import com.bluey683.legacycontrolify.GamepadManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "tick()V", at = @At("TAIL"))
    private void legacyControlify$pollGamepad(CallbackInfo ci) {
        GamepadManager.poll();
    }
}
