package com.cattailsw.retrl.core.sound // Updated package

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO) // Default scope
) {
    private var soundPool: SoundPool? = null
    private var loaded: Boolean = false
    private val soundMap = mutableMapOf<SoundType, Int>()

    enum class SoundType {
        KEY_PRESS,
        CARRIAGE_RETURN,
        BELL
    }

    init {
        coroutineScope.launch {
            initializeSoundPool()
        }
    }

    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME) // Suitable for app interaction sounds
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(5) // Max simultaneous streams
            .build()

        soundPool?.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) {
                // Find which sound type this sampleId belongs to
                val soundType = soundMap.entries.find { it.value == sampleId }?.key
                Log.d("SoundManager", "Loaded sound: $soundType (ID: $sampleId)")
                // Could mark individual sounds as loaded if needed
            } else {
                Log.e("SoundManager", "Error loading sound ID: $sampleId, status: $status")
            }
            // For simplicity, we'll assume all are loaded if no errors are thrown for critical sounds
            // or use a counter to check if all expected sounds are loaded.
            // For now, one successful load will set the 'loaded' flag.
            if (status == 0) loaded = true // Simplified: any successful load marks as generally ready
        }

        // Load sounds - Replace 0 with actual R.raw.resource_name when available
        // TODO: Update these resource IDs when actual sound files are added
        soundMap[SoundType.KEY_PRESS] = soundPool?.load(context, 0 /* R.raw.key_press_1 */, 1) ?: 0
        soundMap[SoundType.CARRIAGE_RETURN] = soundPool?.load(context, 0 /* R.raw.carriage_return_1 */, 1) ?: 0
        soundMap[SoundType.BELL] = soundPool?.load(context, 0 /* R.raw.typewriter_bell_1 */, 1) ?: 0

        // Log attempts to load
        soundMap.forEach { (type, id) ->
            if (id == 0) {
                Log.e("SoundManager", "Failed to initiate loading for $type. SoundPool might be null or load returned 0.")
            } else {
                Log.d("SoundManager", "Attempting to load $type with ID $id")
            }
        }
    }

    fun playSound(soundType: SoundType, volume: Float = 1.0f, rate: Float = 1.0f) {
        if (!loaded) {
            Log.w("SoundManager", "Sound pool not yet loaded or sound $soundType not available.")
            // Optionally, queue the sound or try to load it again.
            return
        }
        val soundId = soundMap[soundType]
        if (soundId != null && soundId != 0) {
            soundPool?.play(soundId, volume, volume, 1, 0, rate)
            Log.d("SoundManager", "Played sound: $soundType (ID: $soundId)")
        } else {
            Log.e("SoundManager", "Sound ID for $soundType not found or is invalid (0).")
        }
    }

    fun release() {
        coroutineScope.launch {
            soundPool?.release()
            soundPool = null
            loaded = false
            soundMap.clear()
            Log.d("SoundManager", "SoundPool released")
        }
    }
}
