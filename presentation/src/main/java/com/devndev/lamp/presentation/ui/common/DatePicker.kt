package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devndev.lamp.presentation.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LampDateTimePicker(
    onDateTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var isTimePickerShow by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    if (isTimePickerShow) {
        Dialog(onDismissRequest = {
            isTimePickerShow = false
            onDismiss()
        }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    TimeInput(state = timePickerState)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = {
                            isTimePickerShow = false
                            onDismiss()
                        }) {
                            Text(text = stringResource(R.string.cancel))
                        }

                        TextButton(onClick = {
                            selectedDateMillis?.let { dateMillis ->
                                val calendar = Calendar.getInstance().apply {
                                    timeInMillis = dateMillis
                                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    set(Calendar.MINUTE, timePickerState.minute)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }
                                val formatter = SimpleDateFormat("yyyy년 M월 d일 HH:mm", Locale.getDefault())
                                val dateTimeString = formatter.format(calendar.time)
                                onDateTimeSelected(dateTimeString)
                            }
                            isTimePickerShow = false
                            onDismiss()
                        }) {
                            Text(text = stringResource(R.string.confirm))
                        }
                    }
                }
            }
        }
        return
    }

    // 날짜 선택 Dialog
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                val selected = datePickerState.selectedDateMillis
                if (selected != null) {
                    selectedDateMillis = selected
                    isTimePickerShow = true
                } else {
                    onDismiss()
                }
            }) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White
                )
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
