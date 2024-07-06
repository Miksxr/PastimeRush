package com.example.pastimerush

import com.example.pastimerush.database.MoodEntry
import com.example.pastimerush.database.MoodEntryDao

class MoodRepository(private val moodEntryDao: MoodEntryDao) {
    suspend fun getAllMoodEntries() = moodEntryDao.getAll()
    suspend fun insertMoodEntry(moodEntry: MoodEntry) = moodEntryDao.insert(moodEntry)
}
