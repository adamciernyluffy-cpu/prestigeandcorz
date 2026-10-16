package dev.orca.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class OrcaClient implements ClientModInitializer {
    public static KeyBinding OPEN_MENU;
    public static KeyBinding AREA_CORNER_ONE;
    public static KeyBinding AREA_CORNER_TWO;

    @Override
    public void onInitializeClient() {
        OPEN_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.orca_client.open_menu", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
        AREA_CORNER_ONE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.orca_client.corner_one", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O, KeyBinding.Category.MISC));
        AREA_CORNER_TWO = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.orca_client.corner_two", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new ClientMenuScreen());
                else if (client.currentScreen instanceof ClientMenuScreen) client.setScreen(null);
            }
            while (AREA_CORNER_ONE.wasPressed()) AreaMinerModule.markCorner(client, true);
            while (AREA_CORNER_TWO.wasPressed()) AreaMinerModule.markCorner(client, false);
        });
    }
}
