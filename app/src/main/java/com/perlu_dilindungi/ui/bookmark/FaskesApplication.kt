package com.perlu_dilindungi.ui.bookmark

import android.app.Application
import com.perlu_dilindungi.data.room.FaskesRoomDatabase

class FaskesApplication: Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: FaskesRoomDatabase by lazy { FaskesRoomDatabase.getDatabase(this) }
}