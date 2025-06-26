package com.cattailsw.retrl.domain

/** Represents a saved document/session. */
data class TypewriterSession(
    val id: String,
    val title: String,
    val createdAt: Long,
    val paperWidthPx: Int,
    val paperHeightPx: Int,
    val events: List<KeystrokeEvent>
)
