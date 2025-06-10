package com.cattailsw.retrl.core.domain.model // Updated package

import kotlinx.serialization.Serializable

@Serializable
data class KeystrokeEvent(
    val char: Char,         // The character typed, or special key identifier
    val timestamp: Long,    // Time of the event in milliseconds since epoch
    val xPosition: Float,   // X coordinate on the virtual paper
    val yPosition: Float,   // Y coordinate on the virtual paper
    val pressure: Float = 1.0f, // Keystroke pressure, if available (e.g., for dynamic ink flow)
    val isShiftHeld: Boolean = false,
    val isCtrlHeld: Boolean = false,
    val isAltHeld: Boolean = false,
    // Add other relevant metadata, e.g., sound type, animation frame for playback
) {
    companion object {
        const val SPECIAL_KEY_BACKSPACE = '\b'
        const val SPECIAL_KEY_NEWLINE = '\n'
        const val SPECIAL_KEY_TAB = '\t'
        // Add other special keys as needed
    }
}
