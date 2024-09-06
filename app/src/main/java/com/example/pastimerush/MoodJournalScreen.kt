package com.example.pastimerush

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pastimerush.data.database.MoodEntry
import com.example.pastimerush.viewmodel.MoodViewModel

@Composable
fun MoodJournalScreen(viewModel: MoodViewModel, onAddMoodClick: () -> Unit) {
    val moodEntries by viewModel.moodEntries.collectAsState()
    var selectedMoodEntry by remember { mutableStateOf<MoodEntry?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    if (showEditDialog && selectedMoodEntry != null) {
        EditMoodDialog(
            moodEntry = selectedMoodEntry!!,
            onDismiss = { showEditDialog = false },
            onSave = { updatedMoodEntry ->
                viewModel.updateMoodEntry(updatedMoodEntry)
                showEditDialog = false
            },
            onDelete = { moodEntryToDelete ->
                viewModel.deleteMoodEntry(moodEntryToDelete)
                showEditDialog = false
            }
        )
    }

    Box(modifier = Modifier.background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Журнал настроения",
                style = TextStyle(fontSize = 24.sp, color = Color.White),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                items(moodEntries) { moodEntry ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedMoodEntry = moodEntry
                                showEditDialog = true
                            },
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val mood = Mood.valueOf(moodEntry.mood)

                            Image(
                                painter = painterResource(id = mood.drawableRes),
                                contentDescription = mood.name,
                                modifier = Modifier.size(64.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "${moodEntry.date} ${moodEntry.time}",
                                    color = Color.Black,
                                    style = TextStyle(fontSize = 16.sp)
                                )
                                Text(
                                    text = moodEntry.note,
                                    color = Color.Gray,
                                    style = TextStyle(fontSize = 14.sp)
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onAddMoodClick,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Добавить новое настроение")
            }
        }
    }
}

@Composable
fun EditMoodDialog(
    moodEntry: MoodEntry,
    onDismiss: () -> Unit,
    onSave: (MoodEntry) -> Unit,
    onDelete: (MoodEntry) -> Unit
) {
    var dateText by remember { mutableStateOf(TextFieldValue(moodEntry.date)) }
    var timeText by remember { mutableStateOf(TextFieldValue(moodEntry.time)) }
    var noteText by remember { mutableStateOf(TextFieldValue(moodEntry.note)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Редактировать запись") },
        text = {
            Column {
                OutlinedTextField(
                    value = dateText,
                    onValueChange = { dateText = it },
                    label = { Text("Дата") }
                )
                OutlinedTextField(
                    value = timeText,
                    onValueChange = { timeText = it },
                    label = { Text("Время") }
                )
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text("Заметка") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedEntry = moodEntry.copy(
                        date = dateText.text,
                        time = timeText.text,
                        note = noteText.text
                    )
                    onSave(updatedEntry)
                }
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDelete(moodEntry) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Заменили backgroundColor на containerColor
            ) {
                Text("Удалить")
            }
        }
    )
}