package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampBigTextField
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen

@Composable
fun LampIntroductionScreen(
    lampName: String,
    lampSummary: String,
    onLampNameChange: (String) -> Unit,
    onLampSummaryChange: (String) -> Unit
) {
    SelectionScreen(text = stringResource(id = R.string.lame_introduction)) {
        var subjectQuery by remember { mutableStateOf(lampName) }
        var summaryQuery by remember { mutableStateOf(lampSummary) }
        val maxSubjectChar = 8
        val maxSummaryChar = 100
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LampTextField(
                width = 270,
                isGradient = false,
                query = subjectQuery,
                onQueryChange = {
                    subjectQuery = it
                    onLampNameChange(it)
                },
                hintText = stringResource(id = R.string.input_lamp_name),
                maxLength = maxSubjectChar,
                radius = 15
            )

            LampBigTextField(
                width = 270,
                height = 120,
                query = summaryQuery,
                onQueryChange = {
                    summaryQuery = it
                    onLampSummaryChange(it)
                },
                hintText = stringResource(id = R.string.input_lamp_summary),
                maxLength = maxSummaryChar
            )
        }
    }
}
