package com.example.pastimerush

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pastimerush.viewmodel.MoodViewModel

@Composable
fun AppNavigation(viewModel: MoodViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "journal") {
        composable("journal") {
            MoodJournalScreen(viewModel) {
                navController.navigate("createMood")
            }
        }
        composable("createMood") {
            CreateMoodScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }
}