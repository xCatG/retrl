package com.cattailsw.retrl.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cattailsw.retrl.domain.SaveSessionUseCase
import com.cattailsw.retrl.domain.TypewriterSession
import com.cattailsw.retrl.sound.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val saveSession: SaveSessionUseCase,
    private val soundManager: SoundManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState

    fun onCharacterTyped(char: Char) {
        soundManager.playKeyClick()
        val event = com.cattailsw.retrl.domain.KeystrokeEvent(
            char = char,
            xOffsetPx = 0f,
            yOffsetPx = 0f,
            timestampMillis = System.currentTimeMillis()
        )
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            drawnEvents = _uiState.value.drawnEvents + event
        )
    }

    fun save() {
        viewModelScope.launch {
            val session = TypewriterSession(
                id = "0",
                title = "Untitled",
                createdAt = System.currentTimeMillis(),
                paperWidthPx = 0,
                paperHeightPx = 0,
                events = _uiState.value.drawnEvents
            )
            saveSession(session)
        }
    }
}
