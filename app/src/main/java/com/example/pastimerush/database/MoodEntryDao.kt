package com.example.pastimerush.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoodEntryDao {
    @Query("SELECT * FROM mood_entries")
    suspend fun getAll(): List<MoodEntry>

    @Insert
    suspend fun insert(moodEntry: MoodEntry)
}