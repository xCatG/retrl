package com.cattailsw.retrl.core.sound.di // Updated package

import android.content.Context
import com.cattailsw.retrl.core.sound.SoundManager // Updated import
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundModule {

    @Provides
    @Singleton
    fun provideApplicationCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default) // Or Dispatchers.IO based on needs
    }

    @Provides
    @Singleton
    fun provideSoundManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope // Injecting the scope
    ): SoundManager {
        return SoundManager(context, coroutineScope)
    }
}
