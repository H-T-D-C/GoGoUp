# Repository Guidelines

## Project Structure & Module Organization

GoGoUp is a Fabric Minecraft mod scaffold targeting Minecraft 26.1.2 and Java 25. Its metadata currently declares a client-only mod.

- `src/main/java/com/velters/gogoup/`: main initialization and shared code.
- `src/client/java/com/velters/gogoup/client/`: client initialization and data generation.
- `src/main/resources/`: `fabric.mod.json` and the main mixin configuration. Place mod assets under `assets/gogoup/` here.
- `src/client/resources/`: client mixin configuration.
- `build.gradle`, `settings.gradle`, and `gradle.properties`: build logic, plugin repositories, and dependency versions.

Fabric Loom combines the main and client source sets into the mod. Keep client-specific Minecraft references in the client source set. Build outputs belong in `build/`; local game files belong in `run/`.

## Build, Test, and Development Commands

Use JDK 25 and the Gradle Wrapper, pinned to 9.8.1 in `gradle/wrapper/gradle-wrapper.properties`. On Windows, run `gradlew.bat`; on macOS/Linux, run `./gradlew`:

- `gradlew.bat build`: compile, run configured checks, and package JARs in `build/libs/`.
- `gradlew.bat runClient`: launch the development Minecraft client.
- `gradlew.bat runDatagen`: invoke the Fabric data generator; add providers in `GogoupDataGenerator` when introducing generated resources.
- `gradlew.bat test`: run Java tests once a test framework and tests are added.

## Coding Style & Naming Conventions

Follow existing files: four-space indentation for Java and Gradle, two spaces for JSON, UTF-8 encoding, and opening braces on the declaration line. Use `UpperCamelCase` for classes, `lowerCamelCase` for methods and variables, and `UPPER_SNAKE_CASE` for constants. Keep packages under `com.velters.gogoup` and resource identifiers under `gogoup`. No formatter or linter is configured.

## Testing Guidelines

No test framework, test sources, or coverage threshold is configured. Add future unit tests under `src/test/java`, mirroring production packages and naming classes `*Test`. For gameplay changes, run `gradlew.bat build` and verify behavior with `gradlew.bat runClient`; record reproduction steps and results.

## Commit & Pull Request Guidelines

The repository has no commits yet, so no established commit convention exists. Use concise imperative subjects, such as `Add client initialization logging`. Keep changes focused. Pull requests should explain behavior changes, link relevant issues, report validation performed, and include screenshots or recordings for visible gameplay changes.
