package dev.orca.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public final class AreaMinerModule {
    private static boolean enabled = false;
    private static BlockPos cornerOne;
    private static BlockPos cornerTwo;

    private AreaMinerModule() {}

    public static boolean isEnabled() { return enabled; }
    public static void setEnabled(boolean value) { enabled = value; }
    public static BlockPos getCornerOne() { return cornerOne; }
    public static BlockPos getCornerTwo() { return cornerTwo; }

    public static void markCorner(MinecraftClient client, boolean first) {
        if (!enabled || client.player == null || client.world == null) return;
        BlockPos target = client.crosshairTarget instanceof net.minecraft.util.hit.BlockHitResult hit
                ? hit.getBlockPos() : client.player.getBlockPos();
        if (first) cornerOne = target.toImmutable();
        else cornerTwo = target.toImmutable();
        client.player.sendMessage(net.minecraft.text.Text.literal(
                (first ? "Area Miner: corner 1 = " : "Area Miner: corner 2 = ") +
                target.getX() + ", " + target.getY() + ", " + target.getZ()), true);
    }

    public static String dimensionsText() {
        if (cornerOne == null || cornerTwo == null) return "Select corners with O and P";
        int x = Math.abs(cornerOne.getX() - cornerTwo.getX()) + 1;
        int y = Math.abs(cornerOne.getY() - cornerTwo.getY()) + 1;
        int z = Math.abs(cornerOne.getZ() - cornerTwo.getZ()) + 1;
        long count = (long) x * y * z;
        return x + " x " + y + " x " + z + " (" + count + " blocks)";
    }
}
