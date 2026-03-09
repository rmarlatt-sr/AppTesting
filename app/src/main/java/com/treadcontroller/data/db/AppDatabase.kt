package com.treadcontroller.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.treadcontroller.data.model.WorkoutSession
import com.treadcontroller.data.model.WorkoutTemplate

@Database(
    entities = [WorkoutTemplate::class, WorkoutSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "treadcontroller.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
