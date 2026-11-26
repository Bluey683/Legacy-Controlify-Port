package com.bluey683.legacycontrolify;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;

/** Controller-friendly on-screen keyboard for entering text and sending chat. */
public final class VirtualKeyboardScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget textField;
    private static final String[] KEYS = {
        "1","2","3","4","5","6","7","8","9","0",
        "Q","W","E","R","T","Y","U","I","O","P",
        "A","S","D","F","G","H","J","K","L",
        "Z","X","C","V","B","N","M"
    };

    public VirtualKeyboardScreen(Screen parent) {
        super(new LiteralText("On-screen Keyboard"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int fieldWidth = Math.min(300, this.width - 30);
        int left = (this.width - fieldWidth) / 2;
        textField = new TextFieldWidget(this.textRenderer, left, 35, fieldWidth, 20, new LiteralText("Type here"));
        textField.setMaxLength(256);
        this.children.add(textField);

        int keyWidth = Math.min(26, (this.width - 30) / 10);
        int gap = 3;
        int total = 10 * keyWidth + 9 * gap;
        int startX = (this.width - total) / 2;
        for (int i = 0; i < KEYS.length; i++) {
            final String key = KEYS[i];
            int row = i < 10 ? 0 : (i < 20 ? 1 : (i < 29 ? 2 : 3));
            int col = i < 10 ? i : (i < 20 ? i - 10 : (i < 29 ? i - 20 : i - 29));
            int rowCount = row == 3 ? 7 : (row == 2 ? 9 : 10);
            int rowWidth = rowCount * keyWidth + (rowCount - 1) * gap;
            int x = (this.width - rowWidth) / 2 + col * (keyWidth + gap);
            int y = 75 + row * 28;
            this.addButton(new ButtonWidget(x, y, keyWidth, 22, new LiteralText(key),
                button -> textField.setText(textField.getText() + key.toLowerCase())));
        }
        int bottom = 75 + 4 * 28;
        this.addButton(new ButtonWidget(this.width / 2 - 100, bottom, 60, 20,
            new LiteralText("Space"), button -> textField.setText(textField.getText() + " ")));
        this.addButton(new ButtonWidget(this.width / 2 - 35, bottom, 60, 20,
            new LiteralText("Back"), button -> {
                String value = textField.getText();
                if (!value.isEmpty()) textField.setText(value.substring(0, value.length() - 1));
            }));
        this.addButton(new ButtonWidget(this.width / 2 + 30, bottom, 70, 20,
            new LiteralText("Clear"), button -> textField.setText("")));
        this.addButton(new ButtonWidget(this.width / 2 - 100, bottom + 25, 95, 20,
            new LiteralText("Send chat"), button -> sendChat()));
        this.addButton(new ButtonWidget(this.width / 2 + 5, bottom + 25, 95, 20,
            new LiteralText("Close"), button -> this.minecraft.openScreen(parent)));
        setInitialFocus(textField);
    }

    private void sendChat() {
        String message = textField.getText().trim();
        if (!message.isEmpty() && this.minecraft.player != null) {
            this.minecraft.player.sendChatMessage(message);
        }
        this.minecraft.openScreen(parent);
    }

    @Override
    public void tick() {
        if (textField != null) textField.tick();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        drawCenteredString(this.textRenderer, "VIRTUAL KEYBOARD", this.width / 2, 15, 0xFFFFFF);
        if (textField != null) textField.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return textField.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textField.keyPressed(keyCode, scanCode, modifiers)) return true;
        if (keyCode == 256) {
            this.minecraft.openScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return textField.mouseClicked(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
