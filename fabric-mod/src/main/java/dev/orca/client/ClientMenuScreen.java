
package dev.orca.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ClientMenuScreen extends Screen {

    private static boolean autoBuilderEnabled = false;

    private final boolean settingsPage;

    private static final int PANEL_WIDTH = 390;
    private static final int PANEL_HEIGHT = 250;
    private static final int SIDEBAR_WIDTH = 105;

    private static final int COLOR_BACKGROUND = 0xE908101B;
    private static final int COLOR_PANEL = 0xF2121D2B;
    private static final int COLOR_SIDEBAR = 0xF20B1420;
    private static final int COLOR_ACCENT = 0xFF70D9FF;
    private static final int COLOR_TEXT = 0xFFF0F6FF;
    private static final int COLOR_MUTED = 0xFF91A5B9;
    private static final int COLOR_CARD = 0xE91B2939;

    public ClientMenuScreen() {
        this(false);
    }

    public ClientMenuScreen(boolean settingsPage) {
        super(Text.literal("Orca Client"));
        this.settingsPage = settingsPage;
    }

    @Override
    protected void init() {
        int panelLeft = (this.width - PANEL_WIDTH) / 2;
        int panelTop = (this.height - PANEL_HEIGHT) / 2;

        int contentLeft = panelLeft + SIDEBAR_WIDTH + 17;
        int contentWidth = PANEL_WIDTH - SIDEBAR_WIDTH - 34;
        int buttonWidth = contentWidth;

        // Sidebar navigation
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Overview"),
                button -> {
                    if (client != null) {
                        client.setScreen(new ClientMenuScreen(false));
                    }
                })
                .dimensions(panelLeft + 10, panelTop + 58, SIDEBAR_WIDTH - 20, 22)
                .build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Settings"),
                button -> {
                    if (client != null) {
                        client.setScreen(new ClientMenuScreen(true));
                    }
                })
                .dimensions(panelLeft + 10, panelTop + 86, SIDEBAR_WIDTH - 20, 22)
                .build());

        if (!settingsPage) {
            // Area Miner module
            addDrawableChild(ButtonWidget.builder(
                    Text.literal(areaMinerLabel()),
                    button -> {
                        AreaMinerModule.setEnabled(!AreaMinerModule.isEnabled());
                        button.setMessage(Text.literal(areaMinerLabel()));
                    })
                    .dimensions(contentLeft, panelTop + 72, buttonWidth, 24)
                    .build());

            // Auto Builder module toggle
            addDrawableChild(ButtonWidget.builder(
                    Text.literal(autoBuilderLabel()),
                    button -> {
                        autoBuilderEnabled = !autoBuilderEnabled;
                        button.setMessage(Text.literal(autoBuilderLabel()));
                    })
                    .dimensions(contentLeft, panelTop + 111, buttonWidth, 24)
                    .build());

            // Reset corner selection / module info is shown in the panel.
            addDrawableChild(ButtonWidget.builder(
                    Text.literal("Refresh menu"),
                    button -> {
                        if (client != null) {
                            client.setScreen(new ClientMenuScreen(false));
                        }
                    })
                    .dimensions(contentLeft, panelTop + 150, buttonWidth, 22)
                    .build());

        } else {
            addDrawableChild(ButtonWidget.builder(
                    Text.literal("Area Miner: " +
                            (AreaMinerModule.isEnabled() ? "Enabled" : "Disabled")),
                    button -> {
                        AreaMinerModule.setEnabled(!AreaMinerModule.isEnabled());
                        button.setMessage(Text.literal(
                                "Area Miner: " +
                                (AreaMinerModule.isEnabled() ? "Enabled" : "Disabled")
                        ));
                    })
                    .dimensions(contentLeft, panelTop + 78, buttonWidth, 24)
                    .build());

            addDrawableChild(ButtonWidget.builder(
                    Text.literal("Auto Builder: " +
                            (autoBuilderEnabled ? "Enabled" : "Disabled")),
                    button -> {
                        autoBuilderEnabled = !autoBuilderEnabled;
                        button.setMessage(Text.literal(
                                "Auto Builder: " +
                                (autoBuilderEnabled ? "Enabled" : "Disabled")
                        ));
                    })
                    .dimensions(contentLeft, panelTop + 116, buttonWidth, 24)
                    .build());

            addDrawableChild(ButtonWidget.builder(
                    Text.literal("Back to overview"),
                    button -> {
                        if (client != null) {
                            client.setScreen(new ClientMenuScreen(false));
                        }
                    })
                    .dimensions(contentLeft, panelTop + 157, buttonWidth, 22)
                    .build());
        }

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Close"),
                button -> close())
                .dimensions(panelLeft + 10, panelTop + PANEL_HEIGHT - 34,
                        SIDEBAR_WIDTH - 20, 22)
                .build());
    }

    private String areaMinerLabel() {
        return "Area Miner  [" +
                (AreaMinerModule.isEnabled() ? "ON" : "OFF") + "]";
    }

    private String autoBuilderLabel() {
        return "Auto Builder  [" +
                (autoBuilderEnabled ? "ON" : "OFF") + "]";
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        // Background overlay. No blur call, to avoid the previous crash.
        context.fill(0, 0, this.width, this.height, 0x99030810);

        // Main panel and sidebar.
        context.fill(left, top, right, bottom, COLOR_PANEL);
        context.fill(left, top, left + SIDEBAR_WIDTH, bottom, COLOR_SIDEBAR);

        // Accent strip and separators.
        context.fill(left, top, right, top + 2, COLOR_ACCENT);
        context.fill(left + SIDEBAR_WIDTH, top + 42,
                left + SIDEBAR_WIDTH + 1, bottom - 12, 0x554C718B);
        context.fill(left + SIDEBAR_WIDTH + 14, top + 42,
                right - 14, top + 43, 0x553C5269);

        // Branding.
        context.drawTextWithShadow(
                textRenderer,
                Text.literal("ORCA"),
                left + 14,
                top + 15,
                COLOR_ACCENT
        );

        context.drawText(
                textRenderer,
                Text.literal("CLIENT"),
                left + 51,
                top + 16,
                COLOR_TEXT,
                false
        );

        context.drawText(
                textRenderer,
                Text.literal("1.21.11"),
                left + 14,
                top + 32,
                COLOR_MUTED,
                false
        );

        // Main content heading.
        int contentLeft = left + SIDEBAR_WIDTH + 17;

        context.drawTextWithShadow(
                textRenderer,
                Text.literal(settingsPage ? "CLIENT SETTINGS" : "MODULES"),
                contentLeft,
                top + 16,
                COLOR_TEXT
        );

        context.drawText(
                textRenderer,
                Text.literal(settingsPage
                        ? "Configure module states"
                        : "Your client at a glance"),
                contentLeft,
                top + 31,
                COLOR_MUTED,
                false
        );

        if (!settingsPage) {
            // Status card.
            context.fill(
                    contentLeft,
                    top + 48,
                    right - 17,
                    top + 65,
                    COLOR_CARD
            );

            context.fill(
                    contentLeft,
                    top + 48,
                    contentLeft + 3,
                    top + 65,
                    COLOR_ACCENT
            );

            context.drawText(
                    textRenderer,
                    Text.literal("AREA SELECTION"),
                    contentLeft + 9,
                    top + 53,
                    COLOR_MUTED,
                    false
            );

            String dimensions = AreaMinerModule.dimensionsText();
            context.drawText(
                    textRenderer,
                    Text.literal(dimensions == null ? "No selection" : dimensions),
                    right - 25 - textRenderer.getWidth(
                            Text.literal(dimensions == null ? "No selection" : dimensions)),
                    top + 53,
                    COLOR_TEXT,
                    false
            );

            context.drawText(
                    textRenderer,
                    Text.literal("Select two corners with O / P"),
                    contentLeft,
                    top + 185,
                    COLOR_MUTED,
                    false
            );

            context.drawText(
                    textRenderer,
                    Text.literal("Right Shift opens the menu"),
                    contentLeft,
                    top + 199,
                    COLOR_MUTED,
                    false
            );
        } else {
            context.drawText(
                    textRenderer,
                    Text.literal("Module switches"),
                    contentLeft,
                    top + 53,
                    COLOR_ACCENT,
                    false
            );

            context.drawText(
                    textRenderer,
                    Text.literal("Area Miner state: " +
                            (AreaMinerModule.isEnabled() ? "ON" : "OFF")),
                    contentLeft,
                    top + 190,
                    COLOR_MUTED,
                    false
            );

            context.drawText(
                    textRenderer,
                    Text.literal("Auto Builder state: " +
                            (autoBuilderEnabled ? "ON" : "OFF")),
                    contentLeft,
                    top + 204,
                    COLOR_MUTED,
                    false
            );
        }

        // Footer.
        context.drawText(
                textRenderer,
                Text.literal("ORCA CLIENT  /  FABRIC"),
                left + 12,
                bottom - 15,
                COLOR_MUTED,
                false
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public static boolean isAutoBuilderEnabled() {
        return autoBuilderEnabled;
    }
}
