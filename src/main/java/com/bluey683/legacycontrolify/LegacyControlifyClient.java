package com.bluey683.legacycontrolify;

import net.fabricmc.api.ClientModInitializer;

/**
 * Bootstrap for the experimental Minecraft 1.14.2 port.
 * Controller discovery and input bindings are not implemented yet.
 */
public final class LegacyControlifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[Legacy Controlify Port] Client initializer loaded.");
    }
}
