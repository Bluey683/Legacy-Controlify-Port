package com.bluey683.legacycontrolify;

import net.fabricmc.api.ClientModInitializer;

public final class LegacyControlifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[Legacy Controlify Port] Initialized; GLFW gamepad polling enabled.");
    }
}
