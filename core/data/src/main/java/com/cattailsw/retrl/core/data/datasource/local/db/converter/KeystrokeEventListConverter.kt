package com.cattailsw.retrl.core.data.datasource.local.db.converter // Updated package

import androidx.room.TypeConverter
import com.cattailsw.retrl.core.domain.model.KeystrokeEvent // Updated import
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer

class KeystrokeEventListConverter {
    private val json = Json { ignoreUnknownKeys = true } // Configure as needed

    @TypeConverter
    fun fromKeystrokeEventList(events: List<KeystrokeEvent>?): String? {
        return events?.let { json.encodeToString(ListSerializer(KeystrokeEvent.serializer()), it) }
    }

    @TypeConverter
    fun toKeystrokeEventList(jsonString: String?): List<KeystrokeEvent>? {
        return jsonString?.let { json.decodeFromString(ListSerializer(KeystrokeEvent.serializer()), it) }
    }
}
