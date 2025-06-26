package com.cattailsw.retrl.sound

import android.content.Context
import android.media.SoundPool
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoundManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val soundPool = SoundPool.Builder().setMaxStreams(2).build()
    private val keyClick = 0

    fun playKeyClick() {
        soundPool.play(keyClick, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}
