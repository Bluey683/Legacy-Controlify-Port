package com.bluey683.legacycontrolify;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.glfw.GLFW;

/**
 * Reads GLFW joystick state. GLFW joystick IDs are stable constants; this
 * implementation intentionally polls the first connected controller.
 */
public final class GamepadManager {
    private static boolean connected;
    private static String name = "No controller";
    private static float leftX;
    private static float leftY;
    private static float rightX;
    private static float rightY;
    private static boolean[] buttons = new boolean[32];

    private GamepadManager() {
    }

    public static void poll() {
        if (!ControlifyConfig.controllerEnabled) {
            connected = false;
            name = "Controller disabled";
            leftX = leftY = rightX = rightY = 0.0F;
            buttons = new boolean[32];
            return;
        }
        int joystick = findController();
        if (joystick == -1) {
            connected = false;
            name = "No controller";
            leftX = leftY = rightX = rightY = 0.0F;
            buttons = new boolean[32];
            return;
        }

        connected = true;
        String joystickName = GLFW.glfwGetJoystickName(joystick);
        name = joystickName == null ? "Unknown controller" : joystickName;

        FloatBuffer axes = GLFW.glfwGetJoystickAxes(joystick);
        if (axes != null) {
            leftX = axis(axes, 0);
            leftY = -axis(axes, 1);
            rightX = axis(axes, 2);
            rightY = axis(axes, 3) * (ControlifyConfig.invertLookY ? 1.0F : -1.0F);
        } else {
            leftX = leftY = rightX = rightY = 0.0F;
        }

        ByteBuffer rawButtons = GLFW.glfwGetJoystickButtons(joystick);
        boolean[] nextButtons = new boolean[rawButtons == null ? 0 : rawButtons.remaining()];
        if (rawButtons != null) {
            for (int i = 0; i < rawButtons.remaining(); i++) {
                nextButtons[i] = rawButtons.get(i) == GLFW.GLFW_PRESS;
            }
        }
        buttons = nextButtons;
    }

    private static int findController() {
        for (int id = GLFW.GLFW_JOYSTICK_1; id <= GLFW.GLFW_JOYSTICK_LAST; id++) {
            if (GLFW.glfwJoystickPresent(id)) {
                return id;
            }
        }
        return -1;
    }

    private static float axis(FloatBuffer axes, int index) {
        if (index >= axes.limit()) {
            return 0.0F;
        }
        float value = axes.get(index);
        return Math.abs(value) < (ControlifyConfig.deadzone / 100.0F) ? 0.0F : clamp(value);
    }

    private static float clamp(float value) {
        return Math.max(-1.0F, Math.min(1.0F, value));
    }

    public static boolean isConnected() {
        return connected;
    }

    public static String getName() {
        return name;
    }

    public static float getLeftX() {
        return leftX;
    }

    public static float getLeftY() {
        return leftY;
    }

    public static float getRightX() {
        return rightX;
    }

    public static float getRightY() {
        return rightY;
    }

    public static boolean isButtonDown(int button) {
        return button >= 0 && button < buttons.length && buttons[button];
    }

    public static int getButtonCount() {
        return buttons.length;
    }
}
