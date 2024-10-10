package com.example.pastimerush

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pastimerush.data.database.MoodEntry
import com.example.pastimerush.viewmodel.MoodViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun CreateMoodScreen(viewModel: MoodViewModel, onMoodSaved: () -> Unit) {
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var dateText by remember { mutableStateOf(TextFieldValue("")) }
    var timeText by remember { mutableStateOf(TextFieldValue("")) }
    var noteText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Добавить запись настроения",
            style = TextStyle(fontSize = 24.sp, color = Color.White),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    showDatePicker(context) { date ->
                        dateText = TextFieldValue(date)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("Выбрать дату")
            }

            Button(
                onClick = {
                    showTimePicker(context) { time ->
                        timeText = TextFieldValue(time)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text("Выбрать время")
            }
        }

        Text(
            text = "Выберите настроение:",
            style = TextStyle(fontSize = 20.sp, color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        MoodSelector(selectedMood) { mood ->
            selectedMood = mood
        }

        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Заметка") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (selectedMood != null && dateText.text.isNotEmpty() && timeText.text.isNotEmpty()) {
                    viewModel.addMoodEntry(
                        MoodEntry(
                            date = dateText.text,
                            time = timeText.text,
                            mood = selectedMood!!.name,
                            note = noteText.text
                        )
                    )
                    selectedMood = null
                    dateText = TextFieldValue("")
                    timeText = TextFieldValue("")
                    noteText = TextFieldValue("")
                    onMoodSaved()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}

@Composable
fun MoodSelector(selectedMood: Mood?, onMoodSelected: (Mood) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Mood.entries.forEach { mood ->
            Image(
                painter = painterResource(id = mood.drawableRes),
                contentDescription = mood.name,
                modifier = Modifier
                    .size(64.dp)
                    .border(
                        width = if (mood == selectedMood) 2.dp else 0.dp,
                        color = if (mood == selectedMood) Color.Blue else Color.Transparent
                    )
                    .clickable { onMoodSelected(mood) }
            )
        }
    }
}

enum class Mood(val drawableRes: Int) {
    HAPPY(R.drawable.great),
    SAD(R.drawable.fine),
    ANGRY(R.drawable.normal),
    RELAXED(R.drawable.badly),
    STRESSED(R.drawable.terrible)
}

@SuppressLint("NewApi")
private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
        onDateSelected("$dayOfMonth/${month + 1}/$year")
    }, 2024, 7, 17)
    datePickerDialog.show()
}

@SuppressLint("NewApi")
private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        onTimeSelected("$hourOfDay:$minute")
    }, 12, 0, true)
    timePickerDialog.show()
}
