# Vintage Typewriter App

## 🛠 Architecture Overview

* **Single-Activity** with **Jetpack Compose** (`ComponentActivity`) as UI host.
* **MVVM**:

  * **ViewModel** handles keystroke logging, canvas state, playback, and export logic.
  * **Repository/Use‑cases** for saving/loading sessions, exporting assets.
* **State flow / Compose State** used throughout:

  * `MutableState<CanvasModel>` for current canvas image and cursor position.
  * `MutableStateFlow<List<KeystrokeEvent>>` in ViewModel for playback restoration.

---

## 🎨 Canvas-Based Typing UI

* Use `Canvas(modifier = Modifier.fillMaxSize()) { … }`.
* Draw characters manually using:

  * `val textMeasurer = rememberTextMeasurer()` ([developer.android.com][1], [blog.kotlin-academy.com][2], [arxiv.org][3], [stackoverflow.com][4])
  * In `DrawScope`, use `drawText(textMeasurer, char, position, style)` to draw each keystroke.
* Cache text layouts via `Modifier.drawWithCache` to minimize overhead ([developer.android.com][1]).
* Maintain a model of typed characters with their offset (x, y), char, timestamp.

---

## ⌨️ Input Handling & IME Integration

* Add a hidden/zero-dimensional `BasicTextField` with:

  ```kotlin
  keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.None),
  keyboardActions = KeyboardActions(default = { /* noop */ }),
  onTextInputStart = disableSuggestions
  ```
* Use `Modifier.onKeyEvent { … }` to intercept keys ([proandroiddev.com][5], [developer.android.com][6]):

  * **Character keys**: push keystroke to ViewModel and update canvas offset.
  * **Backspace**: repurpose as carriage return or strike-through action.
  * **Enter key**: advance to next line.
* Dynamically render each incoming text chunk character-by-character to simulate authentic timing.

---

## 🎧 Sound Effects

* Centralized sound management using `SoundPool`, configured in ViewModel or DI-ed module ([arxiv.org][3], [wookoo.medium.com][7]).
* Play key click, line-return, and carriage-ding sounds.
* Respect user volume/mute settings.

---

## 🌀 Scrolling & Responsive Layout

* **Tablets/Large screens**: display static viewport showing full document.
* **Phones**: auto-scroll viewport to keep the cursor centered.
* Horizontal scroll: use Compose `ScrollState` synced with canvas offset.

---

## ▶️ Keystroke Playback and Export

* ViewModel logs keystroke events: `(char, x, y, timestamp)`.
* Provide playback UI with variable speeds (1×, 2×, 5×).

  * Replay by feeding events to rendering routine with adjusted delays.
* Export options:

  * **Bitmaps**: generate via `Canvas` capture.
  * **PDF**: use Android `PdfDocument`.
  * **Video**: render voice, combine audio + visuals using **MediaCodec** or **FFmpeg** wrapper.

---

## 📦 Export & Saving

* Save session model (list of keystrokes + canvas size) via Room or file.
* Exporting on-demand via ViewModel-triggered use-case:

  * Render frame-by-frame to bitmap and encode.
  * Save to `MediaStore`.

---

## 📁 File Management & Media Permissions

* Use `Scoped Storage`: `MediaStore` to insert images/videos.
* Use SAF (`ACTION_CREATE_DOCUMENT`) for manual Export As.

---

## 🧩 Additional Components

* UI settings:

  * Toggle paper size.
  * Enable/disable page-flip ink-bleed effect.
  * Sound volume control.
* Back side typing: reuse canvas overlay; blend faint ink bleed.

---

## ✅ Composition & Performance

* Use `remember` for `TextMeasurer`, `SoundPool`, and layout caches.
* Draw text with `drawWithCache` for performance ([appt.org][8], [developer.android.com][1], [developer.android.com][6], [stackoverflow.com][9], [medium.com][10]).
* Handle animations (e.g., playback) using `LaunchedEffect` + `Animatable` or `animate*AsState` ([developer.android.com][11]).

---

## 📅 Dependencies & APIs

* **Jetpack Compose**: UI + Canvas + Input modifiers + state management
* `androidx.lifecycle:lifecycle-viewmodel-compose`
* `androidx.soundpool:soundpool` or native `SoundPool`
* `kotlinx.coroutines` for timing and playback control
* `androidx.compose.animation` for visual transitions
* `PdfDocument`, `MediaStore` for exports

---

## 🎯 Summary Table

| Feature                 | Implementation                                      |
| ----------------------- | --------------------------------------------------- |
| Canvas text drawing     | `Canvas`, `drawText`, caching layout                |
| IME & keyboard handling | `BasicTextField`, `onKeyEvent`, disable suggestions |
| Keystroke logging       | ViewModel state flow & model                        |
| Audio feedback          | `SoundPool` in centralized manager                  |
| Scrolling behavior      | Viewport scroll state, responsive conditions        |
| Playback functionality  | Re-render events w/ timing delays                   |
| Export options          | Bitmap, PDF, video via frame/encoder                |
| Storage/permissions     | Scoped storage, SAF, MediaStore                     |
| Settings & UI toggles   | Compose UI, state toggles                           |

---

Let me know if you want to drill into specific modules (e.g., playback engine, video export pipeline), or set up skeleton code for any part.

[1]: https://developer.android.com/develop/ui/compose/graphics/draw/overview?utm_source=chatgpt.com "Graphics in Compose | Jetpack Compose - Android Developers"
[2]: https://blog.kotlin-academy.com/canvas-in-jetpack-compose-c6e7b651fd9b?utm_source=chatgpt.com "Canvas in Jetpack Compose - Kt. Academy"
[3]: https://arxiv.org/abs/1803.00430?utm_source=chatgpt.com "Interactive Sound Rendering on Mobile Devices using Ray-Parameterized Reverberation Filters"
[4]: https://stackoverflow.com/questions/77769933/jetpack-compose-canvas-not-drawing-text-with-correct-style?utm_source=chatgpt.com "Jetpack Compose Canvas not drawing text with correct style"
[5]: https://proandroiddev.com/creating-custom-text-views-in-android-a-fun-exploration-with-sampleview-31c724ccd45b?utm_source=chatgpt.com "Creating Custom Text Views in Android: A Fun Exploration with ..."
[6]: https://developer.android.com/develop/ui/compose/touch-input/keyboard-input/commands?utm_source=chatgpt.com "Handle keyboard actions | Jetpack Compose - Android Developers"
[7]: https://wookoo.medium.com/centralized-sound-management-in-multi-module-android-applications-with-jetpack-compose-using-54246d49fb5d?utm_source=chatgpt.com "Centralized Sound Management in Multi-Module Android ..."
[8]: https://appt.org/en/docs/jetpack-compose/samples/audio-control?utm_source=chatgpt.com "Audio control in Jetpack Compose - Appt.org"
[9]: https://stackoverflow.com/questions/68709122/jetpack-compose-trying-to-get-sound-to-play-in-viewmodel-fun?utm_source=chatgpt.com "Jetpack compose - trying to get sound to play in viewmodel fun"
[10]: https://medium.com/androiddevelopers/problem-solving-in-compose-text-d1dd1feafe4a?utm_source=chatgpt.com "Problem solving in Compose Text - by Alejandra Stamato - Medium"
[11]: https://developer.android.com/develop/ui/compose/animation/introduction?utm_source=chatgpt.com "Animations in Compose - Android Developers"
