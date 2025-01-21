package com.devndev.lamp.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.creation.OptionButton

@Composable
fun InfoInputScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    birth: String,
    onBirthChange: (String) -> Unit,
    selectedOption: String,
    onSelectOption: (String) -> Unit
) {
    var emailQuery by remember { mutableStateOf(email) }
    var birthQuery by remember { mutableStateOf(birth) }

    val options = listOf("남성", "여성")

    SelectionScreen(text = "", space = 40) {
        Text(
            text = stringResource(id = R.string.forgot_password_header),
            color = Color.White,
            style = Typography.normal12,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LampTextField(
                width = 300,
                isGradient = false,
                query = emailQuery,
                onQueryChange = {
                    emailQuery = it
                    onEmailChange(it)
                },
                hintText = stringResource(id = R.string.input_email)
            )
            LampTextField(
                width = 300,
                isGradient = false,
                query = birthQuery,
                onQueryChange = {
                    birthQuery = it
                    onBirthChange(it)
                },
                maxLength = 8,
                hintText = stringResource(id = R.string.forget_password_birth)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                options.forEach { option ->
                    OptionButton(
                        optionText = option,
                        isSelected = selectedOption == option,
                        onSelect = { onSelectOption(option) },
                        width = 145,
                        height = 38
                    )
                }
            }
        }
    }
}
