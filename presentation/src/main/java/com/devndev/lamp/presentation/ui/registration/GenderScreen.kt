package com.devndev.lamp.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.creation.OptionButton

@Composable
fun GenderScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val options = listOf("남성", "여성")
    SelectionScreen(text = stringResource(id = R.string.input_gender)) {
        Spacer(modifier = Modifier.height(30.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            options.forEach { option ->
                OptionButton(
                    optionText = option,
                    isSelected = selectedOption == option,
                    onSelect = { onSelectOption(option) },
                    width = 200,
                    52
                )
            }
        }
    }
}
