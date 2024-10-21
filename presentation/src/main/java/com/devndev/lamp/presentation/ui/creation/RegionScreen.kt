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
fun RegionScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val options = listOf("건대·성수", "신촌·홍대", "강남·잠실", "인천", "경기")
    SelectionScreen(
        text = stringResource(id = R.string.select_region)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        options.forEach { option ->
            OptionButton(
                optionText = option,
                isSelected = selectedOption == option,
                onSelect = { onSelectOption(option) }
            )
        }
    }
}
