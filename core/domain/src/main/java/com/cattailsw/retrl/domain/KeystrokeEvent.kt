package com.cattailsw.retrl.domain

import java.util.UUID

/** Represents a single character event with all necessary metadata. */
data class KeystrokeEvent(
    val id: String = UUID.randomUUID().toString(),
    val char: Char,
    val xOffsetPx: Float,
    val yOffsetPx: Float,
    val timestampMillis: Long
)
