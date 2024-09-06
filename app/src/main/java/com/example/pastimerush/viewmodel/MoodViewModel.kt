package com.example.pastimerush.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastimerush.data.MoodRepository
import com.example.pastimerush.data.MoodRepositoryImpl
import com.example.pastimerush.data.database.MoodEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(private val repository: MoodRepository) : ViewModel() {
    val moodEntries = MutableStateFlow<List<MoodEntry>>(emptyList())

    init {
        viewModelScope.launch {
            repository.getAllMoodEntries().collect(moodEntries)
        }
    }

    fun addMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            repository.insertMoodEntry(moodEntry)
        }
    }

    fun updateMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            repository.updateMoodEntry(moodEntry)
        }
    }

    fun deleteMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch {
            repository.deleteMoodEntry(moodEntry)
        }
    }
}
