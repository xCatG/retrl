package com.cattailsw.retrl.core.data.di // Updated package

import android.content.Context
import androidx.room.Room
import com.cattailsw.retrl.core.data.datasource.file.FileExportDataSource // Updated import
import com.cattailsw.retrl.core.data.datasource.file.MediaStoreFileExportDataSource // Updated import
import com.cattailsw.retrl.core.data.datasource.local.RoomSessionLocalDataSource // Updated import
import com.cattailsw.retrl.core.data.datasource.local.SessionLocalDataSource // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.SessionDao // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.TypewriterDatabase // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.converter.KeystrokeEventListConverter // Updated import
import com.cattailsw.retrl.core.data.repository.DefaultTypewriterRepository // Updated import
import com.cattailsw.retrl.core.domain.repository.TypewriterRepository // Updated import
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTypewriterRepository(
        defaultTypewriterRepository: DefaultTypewriterRepository
    ): TypewriterRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindSessionLocalDataSource(
        roomSessionLocalDataSource: RoomSessionLocalDataSource
    ): SessionLocalDataSource

    @Singleton
    @Binds
    abstract fun bindFileExportDataSource(
        mediaStoreFileExportDataSource: MediaStoreFileExportDataSource
    ): FileExportDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTypewriterDatabase(@ApplicationContext context: Context): TypewriterDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TypewriterDatabase::class.java,
            TypewriterDatabase.DATABASE_NAME
        )
        // Add migrations here if necessary
        // .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration() // NOT for production, for dev only
        .build()
    }

    @Singleton
    @Provides
    fun provideSessionDao(typewriterDatabase: TypewriterDatabase): SessionDao {
        return typewriterDatabase.sessionDao()
    }

    @Singleton
    @Provides
    fun provideKeystrokeEventListConverter(): KeystrokeEventListConverter {
        return KeystrokeEventListConverter()
    }
}
