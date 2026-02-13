package com.cattailsw.retrl

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager(context: Context) {
    private val soundPool: SoundPool
    private val keyClickSoundId: Int
    private val carriageReturnSoundId: Int
    private val bellSoundId: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        // Load sounds
        // Note: These files must exist in res/raw. 
        // We are using placeholders for now.
        keyClickSoundId = soundPool.load(context, R.raw.key_click, 1)
        carriageReturnSoundId = soundPool.load(context, R.raw.carriage_return, 1)
        bellSoundId = soundPool.load(context, R.raw.bell, 1)
    }

    fun playKeyClick() {
        soundPool.play(keyClickSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun playCarriageReturn() {
        soundPool.play(carriageReturnSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun playBell() {
        soundPool.play(bellSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}
