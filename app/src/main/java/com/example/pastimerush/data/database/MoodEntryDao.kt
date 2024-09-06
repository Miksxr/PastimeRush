package com.example.pastimerush.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodEntryDao {
    @Query("SELECT * FROM mood_entries")
    fun getAll(): Flow<List<MoodEntry>>

    @Insert
    suspend fun insert(moodEntry: MoodEntry)

    @Update
    suspend fun update(moodEntry: MoodEntry)

    @Delete
    suspend fun delete(moodEntry: MoodEntry)
}