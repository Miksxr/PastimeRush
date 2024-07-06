package com.example.pastimerush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.room.Room
import com.example.pastimerush.database.MoodDatabase
import com.example.pastimerush.ui.theme.PastimeRushTheme
import com.example.pastimerush.viewmodel.MoodRepository
import com.example.pastimerush.viewmodel.MoodViewModel
import com.example.pastimerush.viewmodel.MoodViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            MoodDatabase::class.java, "mood-database"
        ).build()
        val repository = MoodRepository(db.moodEntryDao())
        val viewModelFactory = MoodViewModelFactory(repository)
        val viewModel: MoodViewModel by viewModels { viewModelFactory }

        setContent {
            PastimeRushTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MoodJournalScreen(viewModel)
                }
            }
        }
    }
}