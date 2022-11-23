package com.example.mafiahelper.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mafiahelper.*

@Entity(tableName = TIMERS_DB_NAME)
data class TimerDbItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = TIMER_DB_COLUMN_MINUTES)
    var minutes: Int,
    @ColumnInfo(name = TIMER_DB_COLUMN_SECONDS)
    var seconds: Int,
)