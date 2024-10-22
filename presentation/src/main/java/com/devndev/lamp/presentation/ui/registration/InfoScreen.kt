package com.devndev.lamp.presentation.ui.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.CustomRadioButton
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun InfoScreen(
    selectedDrinkOption: String,
    selectedSmokeOption: String,
    selectedExerciseOption: String,
    onSelectDrinkOption: (String) -> Unit,
    onSelectSmokeOption: (String) -> Unit,
    onSelectExerciseOption: (String) -> Unit
) {
    SelectionScreen(text = stringResource(id = R.string.input_info)) {
        Spacer(modifier = Modifier.height(40.dp))
        RadioButtonGroup(
            groupText = stringResource(id = R.string.drink),
            options = listOf(
                stringResource(id = R.string.drink_often),
                stringResource(id = R.string.drink_sometimes),
                stringResource(id = R.string.drink_never),
                stringResource(id = R.string.drink_stop)
            ),
            selectedOption = selectedDrinkOption,
            onSelectOption = { selectedOption ->
                onSelectDrinkOption(selectedOption)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        RadioButtonGroup(
            groupText = stringResource(id = R.string.smoke),
            options = listOf(
                stringResource(id = R.string.smoke_often),
                stringResource(id = R.string.smoke_sometimes),
                stringResource(id = R.string.smoke_never),
                stringResource(id = R.string.smoke_stop)
            ),
            selectedOption = selectedSmokeOption,
            onSelectOption = { selectedOption ->
                onSelectSmokeOption(selectedOption)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        RadioButtonGroup(
            groupText = stringResource(id = R.string.exercise),
            options = listOf(
                stringResource(id = R.string.exercise_often),
                stringResource(id = R.string.exercise_sometimes),
                stringResource(id = R.string.exercise_never),
                stringResource(id = R.string.exercise_stop)
            ),
            selectedOption = selectedExerciseOption,
            onSelectOption = { selectedOption ->
                onSelectExerciseOption(selectedOption)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun RadioButtonGroup(
    groupText: String,
    options: List<String>,
    selectedOption: String,
    onSelectOption: (String) -> Unit
) {
    Column(
        modifier = Modifier.widthIn(max = 280.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = groupText, color = Color.White, style = Typography.medium18)
        val rows = options.chunked(2)
        rows.forEach { rowOptions ->
            Row(
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                rowOptions.forEach { option ->
                    RadioButtonWithLabel(
                        modifier = Modifier.weight(1f),
                        selected = selectedOption == option,
                        onClick = { onSelectOption(option) },
                        label = option
                    )
                }
            }
        }
        if (groupText != stringResource(id = R.string.exercise)) {
            Divider(
                color = Gray
            )
        }
    }
}

@Composable
fun RadioButtonWithLabel(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        CustomRadioButton(
            radioSize = 20,
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = Color.White, style = Typography.medium18)
    }
}
