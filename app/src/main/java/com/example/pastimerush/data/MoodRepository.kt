package com.example.pastimerush.data

import com.example.pastimerush.data.database.MoodDatabase
import com.example.pastimerush.data.database.MoodEntry
import com.example.pastimerush.data.database.MoodEntryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoodRepository {
    fun getAllMoodEntries(): Flow<List<MoodEntry>>

    suspend fun insertMoodEntry(moodEntry: MoodEntry)

    suspend fun updateMoodEntry(moodEntry: MoodEntry)

    suspend fun deleteMoodEntry(moodEntry: MoodEntry)
}

class MoodRepositoryImpl @Inject constructor(private val moodEntryDao: MoodEntryDao) : MoodRepository {
    override fun getAllMoodEntries() = moodEntryDao.getAll()

    override suspend fun insertMoodEntry(moodEntry: MoodEntry) = moodEntryDao.insert(moodEntry)

    override suspend fun updateMoodEntry(moodEntry: MoodEntry) = moodEntryDao.update(moodEntry)

    override suspend fun deleteMoodEntry(moodEntry: MoodEntry) = moodEntryDao.delete(moodEntry)
}