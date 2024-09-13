package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen

@Composable
fun PersonnelScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val options = listOf("2:2", "3:3", "4:4", "5:5")
    SelectionScreen(
        text = stringResource(id = R.string.select_personnel)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { option ->
            OptionButton(
                optionText = option,
                isSelected = selectedOption == option,
                onSelect = { onSelectOption(option) }
            )
        }
    }
}
