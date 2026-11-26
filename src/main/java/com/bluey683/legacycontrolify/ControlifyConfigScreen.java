package com.bluey683.legacycontrolify;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

public final class ControlifyConfigScreen {
    private ControlifyConfigScreen() {
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new LiteralText("Legacy Controlify Settings"));

        ConfigCategory controller = builder.getOrCreateCategory(new LiteralText("Controller"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        controller.addEntry(entries.startBooleanToggle(
                new LiteralText("Enable controller"), ControlifyConfig.controllerEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> ControlifyConfig.controllerEnabled = value)
                .build());

        controller.addEntry(entries.startIntSlider(
                new LiteralText("Analog deadzone"), ControlifyConfig.deadzone, 0, 50)
                .setDefaultValue(18)
                .setSaveConsumer(value -> ControlifyConfig.deadzone = value)
                .build());

        controller.addEntry(entries.startBooleanToggle(
                new LiteralText("Invert look Y"), ControlifyConfig.invertLookY)
                .setDefaultValue(false)
                .setSaveConsumer(value -> ControlifyConfig.invertLookY = value)
                .build());

        controller.addEntry(entries.startBooleanToggle(
                new LiteralText("Enable vibration (requires supported backend)"), ControlifyConfig.vibrationEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> ControlifyConfig.vibrationEnabled = value)
                .build());

        builder.setSavingRunnable(ControlifyConfig::save);
        return builder.build();
    }

    public static void open() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.openScreen(create(client.currentScreen));
    }
}
