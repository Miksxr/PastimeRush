package com.example.pastimerush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.pastimerush.ui.theme.PastimeRushTheme
import com.example.pastimerush.viewmodel.MoodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PastimeRushTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}

/**
 * 1. Добавить Hilt(Убрать Factory)
 * 2. Добавить Kotlin DateTime и поменять moodEntry Date -> Instant(+ Конвертор)
 * 3. Создать отдельный экран CreateMood (Поддержать выбор настроения, даты и заметки)
 * 4. Сделать редактирование и удаление заметки
 * 5. Navigation
 * 6. Почитать про корутины
 */