package com.example.mafiahelper.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.example.mafiahelper.*

@Dao
interface Dao {
    @Insert
    fun insertItem(item: TimerDbItem)
//    @Update
//    fun updateItem(item: TimerDbItem)
    @Query("UPDATE $TIMERS_DB_NAME SET minutes = :minutes, seconds = :seconds WHERE id = :id")
    fun updateItem(id: Int, minutes: Int, seconds: Int)
    @Query("DELETE FROM $TIMERS_DB_NAME WHERE id = :id")
    fun deleteItem(id: Int)
    @Query("SELECT * FROM $TIMERS_DB_NAME")
    fun getAllItems(): Flow<List<TimerDbItem>>
    @Query("SELECT COUNT(*) FROM $TIMERS_DB_NAME")
    suspend fun getNumOfElemets(): Int
}