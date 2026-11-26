package com.bluey683.legacycontrolify;

/**
 * Haptic capability boundary. GLFW's joystick API used by this legacy port
 * does not expose rumble output. This class keeps the feature configurable
 * without falsely reporting vibration support.
 */
public final class VibrationManager {
    private VibrationManager() {
    }

    public static boolean isSupported() {
        return false;
    }

    public static void rumble(float lowFrequency, float highFrequency, int durationMillis) {
        if (!ControlifyConfig.vibrationEnabled) {
            return;
        }
        // TODO: connect an SDL3 haptic/rumble backend after validating the
        // LibSDL4J API and native runtime for the target platforms.
    }
}
