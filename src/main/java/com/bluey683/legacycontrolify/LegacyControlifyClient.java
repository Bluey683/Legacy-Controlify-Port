package com.bluey683.legacycontrolify;

import net.fabricmc.api.ClientModInitializer;

public final class LegacyControlifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ControlifyConfig.load();
        System.out.println("[Legacy Controlify Port] Initialized. Press F8 to open controller settings.");
    }
}
