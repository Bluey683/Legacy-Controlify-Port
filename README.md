# Legacy-Controlify-Port

Experimental legacy gamepad-support port targeting **Minecraft 1.14.2**.

## Build target

- Minecraft: 1.14.2
- Fabric Loader: 0.14.25
- Build JDK: Java 21 (Temurin)
- Java bytecode target: Java 8
- Build: Gradle + Fabric Loom
- CI: GitHub Actions

## Current status

This commit adds the initial Fabric project scaffold and CI build workflow.
It is **not yet a functional Controlify port**: controller discovery, GLFW/LWJGL
polling, button mappings, stick handling, configuration, and in-game bindings
still need to be implemented and tested.

The Java 21 toolchain is used to compile the project. The output targets Java 8
bytecode for legacy compatibility; this does not guarantee that Minecraft 1.14.2
or every dependency will run correctly on a Java 21 runtime.

## Build locally

Install JDK 21 and Gradle, then run:

```bash
gradle build
```

The resulting JAR is written to `build/libs/`.
