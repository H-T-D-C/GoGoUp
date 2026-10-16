# Repository Guidelines

## Project Structure & Module Organization

GoGoUp is a server-only Fabric mod for Minecraft 26.1.2 and Java 25. Java sources live in `src/main/java/com/velters/gogoup/`: `GoGoUpMod` initializes the mod, `GoGoUpConfig` handles JSON persistence, `GoGoUpCommands` registers commands, and `HeightLimit` holds the active ceiling. Placement hooks are in `mixin/`.

Resources under `src/main/resources/` include Fabric metadata, Mixin registration, and the three vanilla dimension type overrides in `data/minecraft/dimension_type/`. There is no client source set, asset directory, or automated test suite. Read `README.md` for usage, `PLAN.md` for design and remaining work, and `docs/validation.md` for verification evidence.

## Build, Test, and Development Commands

Use JDK 25 and the Gradle Wrapper (9.8.1):

- `./gradlew build`: compile and package JARs into `build/libs/`.
- `./gradlew clean build`: rebuild from fresh generated outputs.
- `./gradlew runServer`: start the dedicated development server in `run/`; world loading requires its EULA acceptance.
- `./gradlew test`: currently reports no test sources; no framework or coverage target is configured.

On Windows PowerShell, use `.\gradlew.bat`. Keep local worlds, diagnostic logs, and caches out of Git; `run/`, `test/`, `build/`, and `.gradle/` are ignored.

## Coding Style & Naming Conventions

Use four spaces in Java and Gradle, two in JSON, and UTF-8. Use `UpperCamelCase` classes, `lowerCamelCase` methods, and `UPPER_SNAKE_CASE` constants. No formatter or linter is configured.

Keep code server-only. Centralize placement checks in `HeightLimit` and use focused Mixins; avoid item-wide handlers that intercept unrelated uses. Retain vanilla dimension properties except intended height changes. Internal `min_y` and `height` align to 16-block sections; user-facing `maxBuildY` is an inclusive integer coordinate.

## Validation & Pull Requests

Run `build` after source or resource changes. Manually check the configured ceiling and one block above with player placement, buckets, `/setblock`, and `/fill`; cover all dimensions, live changes, persistence, and existing worlds. Record confirmed results separately from pending checks.

Git history currently contains only `init` commits, so no convention is established. Use concise imperative subjects. PRs should describe behavior, compatibility, validation, and limitations; link relevant issues and include screenshots when they clarify visible behavior.
