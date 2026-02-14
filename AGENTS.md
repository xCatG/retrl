# Repository Guidelines

## Project Structure & Module Organization
This repository is a single-module Android app built with Gradle Kotlin DSL.
- Root build files: `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`.
- App module: `app/`.
- Production code: `app/src/main/java/com/cattailsw/retrl/`.
  - Core logic: `TypewriterViewModel.kt`, `SoundManager.kt`, `TypewriterActivity.kt`.
  - Compose UI: `ui/`, `ui/components/`, `ui/theme/`.
- Resources and assets: `app/src/main/res/` (including sound files in `res/raw/`).
- Unit tests: `app/src/test/java/...`.
- Instrumented/UI tests: `app/src/androidTest/java/...`.

## Build, Test, and Development Commands
Run commands from the repo root:
- `./gradlew assembleDebug` builds the debug APK.
- `./gradlew testDebugUnitTest` runs JVM unit tests in `app/src/test`.
- `./gradlew connectedDebugAndroidTest` runs instrumented tests (requires emulator/device).
- `./gradlew lintDebug` runs Android lint checks.
- `./gradlew clean` clears build outputs when debugging stale builds.

## Coding Style & Naming Conventions
- Language: Kotlin (JVM target 11) with Jetpack Compose.
- Indentation: 4 spaces; avoid tabs.
- Naming:
  - Classes/composables: `PascalCase` (for example, `TypewriterToolbar`).
  - Functions/properties/locals: `camelCase` (for example, `handleKey`, `cursorX`).
  - Tests: `*Test.kt` with descriptive method names (for example, `typingUpdatesCanvas`).
- Keep packages under `com.cattailsw.retrl` and place UI-specific code under `ui/`.
- No dedicated formatter plugin is configured; use Android Studio Kotlin formatting and keep lint clean.

## Testing Guidelines
- Frameworks: JUnit4 for unit tests, Compose UI test APIs for instrumented tests.
- Add or update unit tests for ViewModel/logic changes.
- Add or update `androidTest` coverage for UI behavior and input semantics.
- Prefer deterministic assertions over timing-dependent checks.

## Commit & Pull Request Guidelines
- Follow Conventional Commit style seen in history: `feat: ...`, `test: ...`.
- Keep commits focused; avoid vague messages like `update`.
- PRs should include:
  - What changed and why.
  - Test evidence (commands run and outcomes).
  - Screenshots/video for UI changes.
  - Linked issue/task when applicable.

## Security & Configuration Tips
- Do not commit secrets, keystores, or machine-specific overrides.
- Treat `local.properties` as local-only configuration.
