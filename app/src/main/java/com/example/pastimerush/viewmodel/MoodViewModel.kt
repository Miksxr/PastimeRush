package com.example.pastimerush.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastimerush.database.MoodEntry
import kotlinx.coroutines.launch

class MoodViewModel(private val repository: MoodRepository) : ViewModel() {
    val moodEntries = mutableStateListOf<MoodEntry>()

    init {
        viewModelScope.launch {
            moodEntries.addAll(repository.getAllMoodEntries())
        }
    }

    fun addMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            repository.insertMoodEntry(moodEntry)
            moodEntries.add(moodEntry)
        }
    }
}