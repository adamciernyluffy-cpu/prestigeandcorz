package dev.orca.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ClientMenuScreen extends Screen {
    private boolean autoBuilderEnabled = false;

    public ClientMenuScreen() {
        super(Text.literal("Orca Client"));
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 105;
        int top = this.height / 2 - 90;
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Area Miner: " + (AreaMinerModule.isEnabled() ? "ON" : "OFF")),
                button -> {
                    AreaMinerModule.setEnabled(!AreaMinerModule.isEnabled());
                    button.setMessage(Text.literal("Area Miner: " + (AreaMinerModule.isEnabled() ? "ON" : "OFF")));
                }).dimensions(left, top, 210, 20).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Auto Builder: " + (autoBuilderEnabled ? "ON" : "OFF")),
                button -> {
                    autoBuilderEnabled = !autoBuilderEnabled;
                    button.setMessage(Text.literal("Auto Builder: " + (autoBuilderEnabled ? "ON" : "OFF")));
                }).dimensions(left, top + 27, 210, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"),
                button -> close()).dimensions(left, top + 62, 210, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.fill(width / 2 - 125, height / 2 - 112, width / 2 + 125, height / 2 + 105, 0xEE111722);
        context.drawCenteredTextWithShadow(textRenderer, "ORCA CLIENT", width / 2, height / 2 - 101, 0xFFB7E7FF);
        context.drawCenteredTextWithShadow(textRenderer, "Area Miner: " + AreaMinerModule.dimensionsText(),
                width / 2, height / 2 + 7, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "O / P = set corners | Right Shift = menu",
                width / 2, height / 2 + 25, 0xFF9AA9BA);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
