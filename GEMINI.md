# Retrl - Vintage Typewriter App

## Project Overview
**Retrl** is an Android application designed to simulate the experience of a vintage mechanical typewriter. It leverages **Jetpack Compose** for a custom, canvas-based rendering engine to mimic the look and feel of ink on paper, complete with sound effects and export capabilities.

**Current Status:** The project has the core **typing engine** and **sound effects** implemented. The `TypingCanvas` renders text manually using `Canvas` and `TextMeasurer`, and a `SoundManager` plays click/return sounds via `SoundPool` on key events. A `TypewriterViewModel` manages the keystroke state.

## Tech Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Build System:** Gradle (Kotlin DSL)
*   **Minimum SDK:** 26 (Android 8.0)
*   **Target/Compile SDK:** 36 (Android 15)
*   **Architecture Pattern:** MVVM (Planned)

## Key Files & Structure
*   `app/src/main/AndroidManifest.xml`: App manifest.
*   `app/src/main/java/com/cattailsw/retrl/TypewriterActivity.kt`: Main entry point. Contains the `TypingCanvas` placeholder.
*   `app/src/main/java/com/cattailsw/retrl/ui/theme/`: Compose theming (Color, Type, Theme).
*   `gradle/libs.versions.toml`: Version catalog for dependency management.
*   `build.gradle.kts` & `app/build.gradle.kts`: Build configuration.

## Development Conventions

### Build & Run
*   **Build Debug APK:** `./gradlew assembleDebug`
*   **Run Unit Tests:** `./gradlew test`
*   **Run Android Tests:** `./gradlew connectedAndroidTest` (requires connected device/emulator)

### Coding Standards
*   **UI:** Use Jetpack Compose. Avoid XML layouts unless absolutely necessary.
*   **State Management:** Follow Unidirectional Data Flow (UDF) principles.
*   **Resources:** Use `libs.versions.toml` for managing dependency versions.
*   **Formatting:** Follow standard Kotlin coding conventions.

## Architecture (Planned)
*   **UI Layer:** `TypingCanvas` using `Canvas` composable for manual text rendering.
*   **ViewModel:** Handles keystroke events, sound triggers, and state persistence.
*   **Data Layer:** Room database or File storage for session management.
*   **Sound:** `SoundPool` for low-latency audio feedback.

## Roadmap (Immediate Tasks)
1.  [Completed] Implement the `TypingCanvas` to handle basic text input and drawing.
2.  [Completed] Set up the MVVM structure (ViewModel to hold state).
3.  [Completed] Integrate the `SoundPool` for key sounds.
4.  Refine input handling (e.g., margin stop, bell sound at end of line).
