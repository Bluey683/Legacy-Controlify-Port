package com.bluey683.legacycontrolify;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

/**
 * Experimental eight-slot radial selector for the controller.
 * Actions are intentionally represented as labels until the matching
 * Minecraft actions are wired into the input layer.
 */
public final class RadialMenuScreen extends Screen {
    private static final String[] ITEMS = {
        "Inventory", "Crafting", "Map", "Emotes",
        "Drop item", "Swap hand", "Perspective", "Settings"
    };
    private int selected = 0;

    public RadialMenuScreen() {
        super(new LiteralText("Controller Radial Menu"));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        int cx = this.width / 2;
        int cy = this.height / 2;
        drawCenteredString(this.textRenderer, "RADIAL MENU", cx, cy - 12, 0xFFFFFF);
        int radius = 78;
        for (int i = 0; i < ITEMS.length; i++) {
            double angle = -Math.PI / 2.0 + (Math.PI * 2.0 * i / ITEMS.length);
            int x = cx + (int) (Math.cos(angle) * radius);
            int y = cy + (int) (Math.sin(angle) * radius);
            int color = i == selected ? 0xFFFF55 : 0xFFFFFF;
            drawCenteredString(this.textRenderer, ITEMS[i], x, y, color);
        }
        drawCenteredString(this.textRenderer,
            "Use arrows to select, Enter to confirm, Esc to close",
            cx, cy + 105, 0xAAAAAA);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.openScreen(null);
            return true;
        }
        if (keyCode == 263 || keyCode == 265) {
            selected = (selected + ITEMS.length - 1) % ITEMS.length;
            return true;
        }
        if (keyCode == 262 || keyCode == 264) {
            selected = (selected + 1) % ITEMS.length;
            return true;
        }
        if (keyCode == 257 || keyCode == 335) {
            System.out.println("[Legacy Controlify Port] Radial selection: " + ITEMS[selected]
                + " (action binding not implemented yet)");
            this.minecraft.openScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
