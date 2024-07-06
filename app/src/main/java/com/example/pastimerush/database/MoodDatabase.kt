package com.example.pastimerush.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoodEntry::class], version = 1)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao
}