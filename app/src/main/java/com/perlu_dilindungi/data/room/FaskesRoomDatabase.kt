package com.perlu_dilindungi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.perlu_dilindungi.data.HealthCenter

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [HealthCenter::class], version = 2, exportSchema = false)
abstract class FaskesRoomDatabase : RoomDatabase() {

    abstract fun faskesDao(): HealthCenterDao

    companion object {
        @Volatile
        private var INSTANCE: FaskesRoomDatabase? = null

        fun getDatabase(context: Context): FaskesRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FaskesRoomDatabase::class.java,
                    "faskes_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}