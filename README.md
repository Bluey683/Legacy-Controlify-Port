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

## Build

The GitHub Actions workflow uses Temurin Java 21 and Gradle 8.7:

```bash
gradle --no-daemon build
```

The resulting mod JAR is uploaded as the `legacy-controlify-port` workflow artifact.

## Known limitations

This is an early port, not a complete Controlify implementation. Controller input is detected, but axes and buttons are not yet mapped to Minecraft movement, camera, jump, inventory, or configurable bindings. Test the build and in-game screen before using it in a modpack. Java 21 compilation with Java 8 bytecode does not guarantee runtime compatibility on every Java 21 setup.
