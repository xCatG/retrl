package com.cattailsw.retrl.feature.editor.state // Updated package

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cattailsw.retrl.core.domain.model.KeystrokeEvent // Updated import
import com.cattailsw.retrl.core.domain.model.TypewriterSession // Added import

// Represents the configuration of the virtual paper
data class PaperConfig(
    val width: Dp = 800.dp, // Default A4-like width in DP for canvas
    val height: Dp = 1120.dp, // Default A4-like height
    val backgroundColor: Color = Color(0xFFF5F5DC), // VintageCream
    val simulatedPixelRatio: Float = 2.0f // For simulating higher res canvas internally
)

// Represents the state of playback for recorded sessions
data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentEventIndex: Int = 0,
    val playbackSpeed: Float = 1.0f, // 1.0f is normal speed
    val totalDurationMillis: Long = 0L // Estimated total duration of the session
)

// Enum for supported export formats
enum class ExportFormat {
    PNG,
    // PDF, // Future support
    // TXT, // Future support
    // MP4  // Future (complex) support
}

// Main UI state for the editor screen
data class EditorUiState(
    val currentSessionId: String? = null, // Null if new session
    val currentSession: TypewriterSession? = null, // Loaded session data
    val isLoading: Boolean = false,
    val error: String? = null,
    val paperConfig: PaperConfig = PaperConfig(),
    val currentKeystrokes: List<KeystrokeEvent> = emptyList(),
    val currentTextContent: String = "", // Full text as typed
    val lastTypedCharPosition: Pair<Float, Float>? = null, // For cursor/caret
    val playbackState: PlaybackState = PlaybackState(),
    val isSaving: Boolean = false,
    val showExportDialog: Boolean = false,
    val showSettingsDialog: Boolean = false,
    val lastExportedBitmap: Bitmap? = null // For previewing or sharing
    // Add other UI related states:
    // val selectedFont: FontStyle = FontStyle.CLASSIC_COURIER,
    // val selectedInkColor: InkColor = InkColor.BLACK,
    // val showSoundSettings: Boolean = false,
    // etc.
)
