package com.example.data.db.entity // Or your chosen sub-package

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converters.KeystrokeEventListConverter
import com.example.domain.KeystrokeEvent

@Entity(tableName = "sessions")
@TypeConverters(KeystrokeEventListConverter::class)
data class SessionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val createdAt: Long,
    val paperWidthPx: Int,
    val paperHeightPx: Int,
    val events: List<KeystrokeEvent> // Stored as JSON via TypeConverter
)
