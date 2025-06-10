package com.cattailsw.retrl.core.domain.model // Updated package

import com.cattailsw.retrl.core.domain.model.KeystrokeEvent // Updated import
import kotlinx.serialization.Serializable

@Serializable
data class TypewriterSession(
    val sessionId: String,
    val title: String,
    val creationTimestamp: Long, // Milliseconds since epoch
    val lastModifiedTimestamp: Long, // Milliseconds since epoch
    val events: List<KeystrokeEvent> = emptyList(),
    val currentText: String = "", // The full text as it would appear on paper
    val paperType: PaperType = PaperType.STANDARD_A4,
    val fontStyle: FontStyle = FontStyle.CLASSIC_COURIER,
    val inkColor: InkColor = InkColor.BLACK,
    // Add other session-specific metadata
    // e.g., background sound, ambient effects, etc.
)

@Serializable
enum class PaperType {
    STANDARD_A4,
    LEGAL,
    POSTCARD,
    CUSTOM
    // Define properties like dimensions, texture, etc. if needed
}

@Serializable
enum class FontStyle(val fontName: String) {
    CLASSIC_COURIER("Courier"),
    VINTAGE_SCRIPT("VintageScript"), // Placeholder, actual font file needed
    MODERN_SANS("ModernSans")      // Placeholder
    // Define properties like character sets, metrics, etc.
}

@Serializable
enum class InkColor(val hexColor: String) {
    BLACK("#000000"),
    BLUE_BLACK("#1A237E"),
    RED("#B71C1C"),
    GREEN("#1B5E20")
    // Define properties like opacity, shimmer, etc.
}
