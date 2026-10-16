# Orca Client — Minecraft Java 1.21.11

A starter project for a custom launcher UI and a Fabric client mod. The project is intentionally transparent: it does **not** inject into another process or attempt to bypass anti-cheat.

## Included in this source package

- Fabric mod project targeting Minecraft Java 1.21.11
- Right Shift keybind to open the client menu
- In-game GUI with module toggles
- Area Miner selection utility: mark two corners, render the selected cuboid, and show its dimensions/block count
- Auto Builder module/settings UI scaffold
- Simple Java Swing launcher UI source
- GitHub Actions workflow to build the Fabric mod JAR

## Important implementation status

This is a **starter build**, not a finished 1:1 recreation of Prestige or Corz. The Area Miner currently selects and visualizes an area; it does not automatically destroy blocks. Auto Builder currently provides the module/settings framework but does not yet parse `.litematic`, `.schem`, or `.nbt` files or place an entire schematic. Those require additional implementation and testing against the exact 1.21.11 mappings and server rules.

The project keeps automated block actions disabled by default and is intended for singleplayer or environments where you have permission to use automation.

## Build the Fabric mod

1. Install JDK 21.
2. Open this folder in IntelliJ IDEA, or push it to a GitHub repository.
3. Run `gradle build` (or use the included GitHub Actions workflow).
4. The mod JAR will be in `fabric-mod/build/libs/`.

The GitHub Actions workflow installs Java and Gradle and uploads the JAR as an artifact.

## Launcher

`launcher/OrcaLauncher.java` is a lightweight UI shell. It can open the official Minecraft Launcher and the project folder. It does not handle Microsoft authentication or download Minecraft assets; use the official launcher for those tasks.

## Keybinds

- **Right Shift** — open/close the client menu
- **Area Miner** — enable it in the menu, then use the selection keybinds shown in the module screen to mark corners (selection helpers are designed for singleplayer/authorized worlds).

## License

This starter project is provided for personal learning and modification.
