package com.bluey683.legacycontrolify;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

/** Maps the right stick and A button to a virtual pointer while a GUI is open. */
public final class VirtualMouse {
    private static double x = 0;
    private static double y = 0;
    private static boolean initialized = false;
    private static boolean previousClick = false;
    private static boolean enabled = true;

    private VirtualMouse() { }

    public static void tick(MinecraftClient client) {
        Screen screen = client.currentScreen;
        if (!enabled || screen == null || !GamepadManager.isConnected()) {
            initialized = false;
            previousClick = false;
            return;
        }
        if (!initialized) {
            x = client.getWindow().getScaledWidth() / 2.0;
            y = client.getWindow().getScaledHeight() / 2.0;
            initialized = true;
        }
        x = clamp(x + GamepadManager.getRightX() * 8.0, 0, client.getWindow().getScaledWidth() - 1);
        y = clamp(y + GamepadManager.getRightY() * 8.0, 0, client.getWindow().getScaledHeight() - 1);
        screen.mouseMoved(x, y);
        boolean click = GamepadManager.isButtonDown(0);
        if (click && !previousClick) screen.mouseClicked(x, y, GLFW.GLFW_MOUSE_BUTTON_LEFT);
        if (!click && previousClick) screen.mouseReleased(x, y, GLFW.GLFW_MOUSE_BUTTON_LEFT);
        previousClick = click;
    }

    public static boolean isVisible(MinecraftClient client) {
        return enabled && initialized && client.currentScreen != null && GamepadManager.isConnected();
    }

    public static int getX() { return (int) x; }
    public static int getY() { return (int) y; }

    public static void toggle() {
        enabled = !enabled;
        initialized = false;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
