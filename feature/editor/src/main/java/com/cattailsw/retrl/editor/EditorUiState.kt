package com.cattailsw.retrl.editor

import androidx.compose.ui.geometry.Offset
import com.cattailsw.retrl.domain.KeystrokeEvent

data class EditorUiState(
    val isLoading: Boolean = true,
    val drawnEvents: List<KeystrokeEvent> = emptyList(),
    val currentCursor: Offset = Offset.Zero
)
