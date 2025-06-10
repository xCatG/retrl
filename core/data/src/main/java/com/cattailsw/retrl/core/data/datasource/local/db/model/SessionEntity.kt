package com.cattailsw.retrl.core.data.datasource.local.db.model // Updated package

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cattailsw.retrl.core.domain.model.InkColor
import com.cattailsw.retrl.core.domain.model.PaperType
import com.cattailsw.retrl.core.domain.model.FontStyle

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey
    val sessionId: String,
    val title: String,
    val creationTimestamp: Long,
    val lastModifiedTimestamp: Long,
    val eventsJson: String, // JSON string of List<KeystrokeEvent>
    val currentText: String,
    val paperType: PaperType, // Room will store enum as String by default
    val fontStyle: FontStyle, // Room will store enum as String by default
    val inkColor: InkColor    // Room will store enum as String by default
)
