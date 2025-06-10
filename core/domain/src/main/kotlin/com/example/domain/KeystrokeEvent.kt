package com.example.domain

import java.util.UUID

data class KeystrokeEvent(
    val id: String = UUID.randomUUID().toString(),
    val char: Char,
    val xOffsetPx: Float,
    val yOffsetPx: Float,
    val timestampMillis: Long
)
