package com.example.data.db.converters

import androidx.room.TypeConverter
import com.example.domain.KeystrokeEvent // From :core:domain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KeystrokeEventListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromKeystrokeEventList(events: List<KeystrokeEvent>?): String? {
        return events?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toKeystrokeEventList(json: String?): List<KeystrokeEvent>? {
        val type = object : TypeToken<List<KeystrokeEvent>>() {}.type
        return json?.let { gson.fromJson(it, type) }
    }
}
