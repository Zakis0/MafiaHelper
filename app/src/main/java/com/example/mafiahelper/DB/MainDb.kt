package com.example.mafiahelper.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mafiahelper.*

@Database(entities = [TimerDbItem::class], version = 1)
abstract class MainDb: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                TIMERS_DB_FILE
            ).build()
        }
    }
}