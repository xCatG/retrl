package com.cattailsw.retrl.feature.editor.viewmodel // Updated package

import android.graphics.Bitmap
// import android.view.KeyEvent // Android KeyEvent, if directly used from a View system
import androidx.compose.ui.input.key.KeyEvent // Compose KeyEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cattailsw.retrl.core.domain.model.KeystrokeEvent // Updated import
import com.cattailsw.retrl.core.domain.model.TypewriterSession // Updated import
import com.cattailsw.retrl.core.domain.usecase.ExportCanvasAsBitmapUseCase // Updated import
import com.cattailsw.retrl.core.domain.usecase.LoadSessionUseCase // Updated import
import com.cattailsw.retrl.core.domain.usecase.SaveSessionUseCase // Updated import
import com.cattailsw.retrl.core.domain.util.Result // Updated import
import com.cattailsw.retrl.core.sound.SoundManager // Updated import
import com.cattailsw.retrl.feature.editor.navigation.SESSION_ID_ARG // Updated import
import com.cattailsw.retrl.feature.editor.state.EditorUiState // Updated import
import com.cattailsw.retrl.feature.editor.state.ExportFormat // Updated import
import com.cattailsw.retrl.feature.editor.state.PaperConfig // Updated import
import com.cattailsw.retrl.feature.editor.state.PlaybackState // Updated import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val loadSessionUseCase: LoadSessionUseCase,
    private val exportCanvasAsBitmapUseCase: ExportCanvasAsBitmapUseCase,
    private val soundManager: SoundManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default // Inject dispatcher for testability
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    private var playbackJob: Job? = null

    init {
        viewModelScope.launch(defaultDispatcher) {
            val sessionId: String? = savedStateHandle[SESSION_ID_ARG]
            if (sessionId != null) {
                _uiState.update { it.copy(isLoading = true, currentSessionId = sessionId) }
                when (val result = loadSessionUseCase(sessionId)) {
                    is Result.Success -> {
                        val session = result.data
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentSession = session,
                                currentKeystrokes = session.events,
                                currentTextContent = session.currentText,
                                // TODO: Potentially update paperConfig, font, ink from session
                            )
                        }
                    }
                    is Result.Failure -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = "Failed to load session: ${result.exception.message}")
                        }
                    }
                }
            } else {
                // New session, initialize with a unique ID
                val newSessionId = UUID.randomUUID().toString()
                _uiState.update { it.copy(currentSessionId = newSessionId) }
                // Optionally, create a default TypewriterSession object here or when saving
            }
        }
    }

    fun onKeyEvent(event: androidx.compose.ui.input.key.KeyEvent, xPos: Float, yPos: Float) { // Fully qualified KeyEvent
        // This is a simplified key event to char mapping.
        // A more robust solution would handle dead keys, combinations, locales, etc.
        // For now, assuming simple ASCII or directly usable characters from key events.
        // Also, this doesn't differentiate between key down/up if the event doesn't.
        // Compose KeyEvent is typically on Down.

        // TODO: This mapping is VERY basic. Needs proper implementation.
        val char = mapKeyEventToChar(event) // Placeholder for actual mapping

        if (char != null) {
            soundManager.playSound(SoundManager.SoundType.KEY_PRESS)
            val keystroke = KeystrokeEvent(
                char = char,
                timestamp = System.currentTimeMillis(),
                xPosition = xPos, // This needs to be accurately determined on canvas
                yPosition = yPos, // This needs to be accurately determined on canvas
                isShiftHeld = event.keyModifiers.isShiftPressed,
                isCtrlHeld = event.keyModifiers.isCtrlPressed,
                isAltHeld = event.keyModifiers.isAltPressed
            )
            _uiState.update {
                val newKeystrokes = it.currentKeystrokes + keystroke
                // This text update is also basic. Needs to handle backspace, newline etc.
                val newText = if (char == KeystrokeEvent.SPECIAL_KEY_BACKSPACE) {
                    it.currentTextContent.dropLast(1)
                } else {
                    it.currentTextContent + char
                }
                it.copy(
                    currentKeystrokes = newKeystrokes,
                    currentTextContent = newText,
                    lastTypedCharPosition = Pair(xPos, yPos)
                )
            }
        } else {
            // Handle special keys like Enter, Backspace if not mapped to char
            when {
                // Example: event.key == Key.Enter -> handleNewline()
                // Example: event.key == Key.Backspace -> handleBackspace()
            }
        }
    }


    fun onSaveSession(title: String? = null) {
        viewModelScope.launch(defaultDispatcher) {
            _uiState.update { it.copy(isSaving = true) }
            val currentSessionId = _uiState.value.currentSessionId ?: UUID.randomUUID().toString()
            val sessionToSave = _uiState.value.currentSession?.copy(
                sessionId = currentSessionId,
                title = title ?: _uiState.value.currentSession?.title ?: "Untitled Session",
                lastModifiedTimestamp = System.currentTimeMillis(),
                events = _uiState.value.currentKeystrokes,
                currentText = _uiState.value.currentTextContent
                // paperType, fontStyle, inkColor could also be updated if changed in UI
            ) ?: TypewriterSession( // Create new if null
                sessionId = currentSessionId,
                title = title ?: "Untitled Session ${System.currentTimeMillis()}",
                creationTimestamp = System.currentTimeMillis(),
                lastModifiedTimestamp = System.currentTimeMillis(),
                events = _uiState.value.currentKeystrokes,
                currentText = _uiState.value.currentTextContent
                // Set default paperType, fontStyle, inkColor
            )

            when (val result = saveSessionUseCase(sessionToSave)) {
                is Result.Success -> _uiState.update { it.copy(isSaving = false, currentSession = sessionToSave, error = null) }
                is Result.Failure -> _uiState.update {
                    it.copy(isSaving = false, error = "Failed to save session: ${result.exception.message}")
                }
            }
        }
    }

    fun onExport(bitmap: Bitmap, title: String, format: ExportFormat) {
        viewModelScope.launch(defaultDispatcher) {
            _uiState.update { it.copy(isSaving = true) } // Re-use isSaving for export progress
            when (format) {
                ExportFormat.PNG -> {
                    when (val result = exportCanvasAsBitmapUseCase(bitmap, title)) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(isSaving = false, lastExportedBitmap = bitmap, error = "Exported to ${result.data}")
                            }
                        }
                        is Result.Failure -> {
                            _uiState.update {
                                it.copy(isSaving = false, error = "Export failed: ${result.exception.message}")
                            }
                        }
                    }
                }
                // Handle other formats (PDF, TXT, MP4) when implemented
                // else -> _uiState.update { it.copy(isSaving = false, error = "Export format not supported yet") }
            }
        }
    }


    fun onClearCanvas() {
        _uiState.update {
            it.copy(
                currentKeystrokes = emptyList(),
                currentTextContent = "",
                lastTypedCharPosition = null
                // Potentially reset other state like currentSession if it implies starting fresh
            )
        }
        // Optionally save the cleared session or prompt user
    }

    fun onTogglePlayback() {
        val isPlaying = _uiState.value.playbackState.isPlaying
        if (isPlaying) {
            playbackJob?.cancel()
            _uiState.update { it.copy(playbackState = it.playbackState.copy(isPlaying = false)) }
        } else {
            _uiState.update { it.copy(playbackState = it.playbackState.copy(isPlaying = true, currentEventIndex = 0)) }
            startPlayback()
        }
    }

    private fun startPlayback() {
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch(defaultDispatcher) {
            val events = _uiState.value.currentKeystrokes
            if (events.isEmpty()) {
                _uiState.update { it.copy(playbackState = it.playbackState.copy(isPlaying = false)) }
                return@launch
            }

            var lastEventTime = events.first().timestamp
            for (i in _uiState.value.playbackState.currentEventIndex until events.size) {
                if (!_uiState.value.playbackState.isPlaying) break // Check if playback was stopped

                val event = events[i]
                val delayMillis = (event.timestamp - lastEventTime) / _uiState.value.playbackState.playbackSpeed.toLong()
                if (delayMillis > 0) {
                    delay(delayMillis)
                }
                lastEventTime = event.timestamp

                // Simulate the event on the UI
                // This is tricky. The UI needs to observe this and draw.
                // For now, just update the text content as a simple simulation.
                withContext(Dispatchers.Main) { // Ensure UI updates are on the main thread
                    _uiState.update {
                        it.copy(
                            // This is a very simplified representation of playback
                            // A real playback would need to re-draw characters one by one on canvas
                            currentTextContent = it.currentTextContent + event.char, // Simplified
                            playbackState = it.playbackState.copy(currentEventIndex = i + 1),
                            lastTypedCharPosition = Pair(event.xPosition, event.yPosition) // For cursor
                        )
                    }
                    soundManager.playSound(SoundManager.SoundType.KEY_PRESS) // Play sound for each event
                }
            }
            // Playback finished
            if (_uiState.value.playbackState.isPlaying) { // If not stopped prematurely
                _uiState.update { it.copy(playbackState = it.playbackState.copy(isPlaying = false, currentEventIndex = 0)) }
            }
        }
    }


    fun onPlaybackSpeedChange(speed: Float) {
        _uiState.update { it.copy(playbackState = it.playbackState.copy(playbackSpeed = speed)) }
    }

    fun onShowExportDialog(show: Boolean) {
        _uiState.update { it.copy(showExportDialog = show) }
    }

    fun onShowSettingsDialog(show: Boolean) {
        _uiState.update { it.copy(showSettingsDialog = show) }
    }

    fun onPaperConfigChange(newConfig: PaperConfig) {
        _uiState.update { it.copy(paperConfig = newConfig) }
    }

    // Placeholder for mapping Compose KeyEvent to a Char.
    // This needs significant improvement for a real app.
    private fun mapKeyEventToChar(event: KeyEvent): Char? {
        // This is extremely basic, does not handle modifiers like Shift for symbols,
        // different keyboard layouts, dead keys, etc.
        // It also assumes the key directly maps to a character.
        // val key = event.key
        // return if (key.isLetterOrDigit) key.toString().first() else null // This is not how Key works
        // A more correct, but still limited, approach for simple cases:
        // return event.utf16CodePoint.toChar() // This might give control chars too
        // For now, returning a placeholder or '?'
        // Log.d("EditorViewModel", "KeyEvent: ${event.key}, Unicode: ${event.utf16CodePoint}")
        // if (event.type == KeyEventType.KeyDown) { // Process only key down
        //     return event.utf16CodePoint.toChar().takeIf { it.isDefined() && !it.isISOControl() }
        // }
        // return null
        // The above is also not quite right. For now, let's assume a very direct mapping for testing.
        // This part is highly dependent on how `KeyboardInterceptorComposable` would feed events.
        // If it pre-processes into chars, this function is simpler.
        // If it sends raw KeyEvents, this needs to be complex.
        // For the purpose of this scaffolding, we'll make a gross simplification.
        // This should be replaced by a proper input handling mechanism.
        return 'A' // Placeholder - REMOVE AND REPLACE
    }


    override fun onCleared() {
        super.onCleared()
        playbackJob?.cancel()
        soundManager.release() // Release sound pool resources
    }
}
