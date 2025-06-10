package com.example.editor.state // Or your chosen package

import androidx.compose.ui.geometry.Offset
import com.example.domain.KeystrokeEvent // From :core:domain

// Placeholder for PaperConfig, define it simply for now
data class PaperConfig(val width: Int, val height: Int) {
    companion object {
        fun default() = PaperConfig(1080, 1920) // Example default
    }
}

// Placeholder for PlaybackState
sealed class PlaybackState {
    object Idle : PlaybackState()
    data class Playing(val speed: Float) : PlaybackState()
    // Add other states like Paused, Finished as needed
}

data class EditorUiState(
    val isLoading: Boolean = true,
    val paperConfig: PaperConfig = PaperConfig.default(),
    val drawnEvents: List<KeystrokeEvent> = emptyList(),
    val currentCursorPosition: Offset = Offset.Zero,
    val playbackState: PlaybackState = PlaybackState.Idle,
    val soundEnabled: Boolean = true
)
