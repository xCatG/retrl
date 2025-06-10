package com.cattailsw.retrl.core.data.datasource.local.db // Updated package

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cattailsw.retrl.core.data.datasource.local.db.converter.KeystrokeEventListConverter // Updated import
import com.cattailsw.retrl.core.data.datasource.local.db.model.SessionEntity // Updated import

@Database(
    entities = [SessionEntity::class],
    version = 1, // Increment on schema changes
    exportSchema = true // Recommended: Export schema to a folder for version control
)
@TypeConverters(KeystrokeEventListConverter::class)
abstract class TypewriterDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TypewriterDatabase? = null

        // Database name
        const val DATABASE_NAME = "typewriter_database"

        // Fun to get or create the database instance (typically in DI module)
        // fun getDatabase(context: Context): TypewriterDatabase {
        //     return INSTANCE ?: synchronized(this) {
        //         val instance = Room.databaseBuilder(
        //             context.applicationContext,
        //             TypewriterDatabase::class.java,
        //             DATABASE_NAME
        //         )
        //         // Add migrations if necessary
        //         // .addMigrations(MIGRATION_1_2)
        //         .build()
        //         INSTANCE = instance
        //         instance
        //     }
        // }
    }
}
