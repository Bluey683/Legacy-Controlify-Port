# Legacy Controlify Port

Experimental Fabric port for Minecraft 1.14.2.

## Target

- Minecraft 1.14.2
- Fabric Loader 0.14.25
- Java 21 toolchain (Java 8 bytecode target)
- Cloth Config 1.8

## Current implementation

- GLFW joystick discovery and polling.
- Reads controller name, axes and buttons.
- Cloth Config settings screen, opened in-game with **F8**.
- Settings saved to `config/legacy-controlify.properties`.
- Configurable controller enable toggle, analog deadzone (0–50%), and look Y inversion.

## Controls / configuration

Press **F8** to open the controller settings screen. The GUI is built with Cloth Config. The current input backend is still GLFW; LibSDL4J is included as a dependency but has not yet replaced the GLFW polling backend.

## Experimental additions\n\n- Eight-slot radial selector opened with **F7**; navigate with arrow keys and confirm with Enter. Entries are currently visual placeholders and are not yet bound to Minecraft actions.\n- Vibration setting is present in Cloth Config. Actual rumble is **not available yet**: the current GLFW polling backend has no rumble output, and the SDL3 haptics backend remains to be implemented and tested. `VibrationManager.isSupported()` therefore returns `false`.\n\n## Virtual GUI input

- **F4** toggles the experimental virtual pointer; while a GUI is open, the right stick moves it and controller button 0 clicks.
- **F6** opens an on-screen keyboard with letter/number buttons, Space, Backspace, Clear, and **Send chat**.
- The keyboard's Send chat button submits the entered text as a normal player chat message.

The virtual pointer is an experimental GUI click bridge, not a complete replacement for mouse input. The on-screen keyboard currently sends chat directly; it does not yet inject text into arbitrary focused GUI text fields. Controller button numbering can vary by device.

## Build

The GitHub Actions workflow uses Temurin Java 21 and Gradle 8.7:

```bash
gradle --no-daemon build
```

The resulting mod JAR is uploaded as the `legacy-controlify-port` workflow artifact.

## Known limitations

This is an early port, not a complete Controlify implementation. Controller input is detected, but axes and buttons are not yet mapped to Minecraft movement, camera, jump, inventory, or configurable bindings. Test the build and in-game screen before using it in a modpack. Java 21 compilation with Java 8 bytecode does not guarantee runtime compatibility on every Java 21 setup.
