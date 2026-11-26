package com.bluey683.legacycontrolify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public final class ControlifyConfig {
    public static int deadzone = 18;
    public static boolean controllerEnabled = true;
    public static boolean invertLookY = false;
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("legacy-controlify.properties");

    private ControlifyConfig() {
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(FILE)) {
            properties.load(input);
            deadzone = clamp(parseInt(properties.getProperty("deadzone"), 18), 0, 50);
            controllerEnabled = Boolean.parseBoolean(properties.getProperty("controllerEnabled", "true"));
            invertLookY = Boolean.parseBoolean(properties.getProperty("invertLookY", "false"));
        } catch (IOException exception) {
            System.err.println("[Legacy Controlify Port] Could not load config: " + exception.getMessage());
        }
    }

    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("deadzone", Integer.toString(clamp(deadzone, 0, 50)));
        properties.setProperty("controllerEnabled", Boolean.toString(controllerEnabled));
        properties.setProperty("invertLookY", Boolean.toString(invertLookY));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream output = Files.newOutputStream(FILE)) {
                properties.store(output, "Legacy Controlify Port settings");
            }
        } catch (IOException exception) {
            System.err.println("[Legacy Controlify Port] Could not save config: " + exception.getMessage());
        }
    }

    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return fallback;
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
