package com.example.pastimerush

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import kotlinx.datetime.*
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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun CreateMoodScreen(viewModel: MoodViewModel, onMoodSaved: () -> Unit) {
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var timestamp by remember { mutableStateOf(Clock.System.now()) }
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
                    showDatePicker(context, timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).date) { date ->
                        val localDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                        timestamp = LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, localDateTime.hour, localDateTime.minute)
                            .toInstant(TimeZone.currentSystemDefault())
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
                    showTimePicker(context, timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).time) { time ->
                        val localDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                        timestamp = LocalDateTime(localDateTime.date, time)
                            .toInstant(TimeZone.currentSystemDefault())
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
                if (selectedMood != null) {
                    viewModel.addMoodEntry(
                        MoodEntry(
                            timestamp = timestamp,
                            mood = selectedMood!!.name,
                            note = noteText.text
                        )
                    )
                    selectedMood = null
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
fun showDatePicker(context: Context, initialDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
        onDateSelected(LocalDate(year, month + 1, dayOfMonth))
    }, initialDate.year, initialDate.monthNumber - 1, initialDate.dayOfMonth)
    datePickerDialog.show()
}

@SuppressLint("NewApi")
fun showTimePicker(context: Context, initialTime: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        onTimeSelected(LocalTime(hourOfDay, minute))
    }, initialTime.hour, initialTime.minute, true)
    timePickerDialog.show()
}
