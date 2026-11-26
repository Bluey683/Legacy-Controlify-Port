package com.bluey683.legacycontrolify;

import net.fabricmc.api.ClientModInitializer;

/**
 * Bootstrap for the experimental Minecraft 1.14.2 port.
 *
 * Gamepad polling and input bindings still need to be implemented against
 * the exact GLFW/LWJGL version bundled by Minecraft 1.14.2.
 */
public final class LegacyControlifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[Legacy Controlify Port] Client initializer loaded.");
    }
}
