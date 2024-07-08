package com.example.pastimerush

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pastimerush.database.MoodEntry
import com.example.pastimerush.viewmodel.MoodViewModel


@Composable
fun MoodJournalScreen(viewModel: MoodViewModel) {
    var moodText by remember { mutableStateOf(TextFieldValue("")) }
    var dateText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Box(modifier = Modifier.background(Color.Black)) { // Задний фон теперь черный
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(
                text = "Журнал настроения",
                style = TextStyle(fontSize = 24.sp, color = Color.White), // Текст теперь белый
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                items(viewModel.moodEntries) { moodEntry ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        color = Color.White // Изменен цвет фона элемента списка
                    ) {
                        Text(
                            text = "${moodEntry.date}: ${moodEntry.mood}",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black // Текст теперь белый
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = moodText,
                    onValueChange = { moodText = it },
                    label = { Text("Настроение") },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Button(onClick = {
                    showTimePicker(context) { time ->
                        dateText = TextFieldValue(time)
                    }
                }, modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    Text("Выбрать время")
                }
            }

            Button(
                onClick = {
                    if (moodText.text.isNotEmpty() && dateText.text.isNotEmpty()) {
                        viewModel.addMoodEntry(MoodEntry(date = dateText.text, mood = moodText.text))
                        moodText = TextFieldValue("")
                        dateText = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Добавить")
            }
        }
    }
}

@SuppressLint("NewApi")
private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        onTimeSelected("$hourOfDay:$minute")
    }, 12, 0, true)
    timePickerDialog.show()
}