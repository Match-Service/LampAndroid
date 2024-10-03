package com.devndev.lamp.presentation.ui.registration

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
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun NameScreen(
    name: String,
    onNameChange: (String) -> Unit,
    isValidName: Boolean,
    isDuplicateName: Boolean
) {
    SelectionScreen(text = stringResource(id = R.string.registration_name)) {
        var nameQuery by remember { mutableStateOf(name) }
        val maxNameChar = 6
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampTextField(
                width = 270,
                isGradient = !isValidName || isDuplicateName,
                query = nameQuery,
                onQueryChange = {
                    nameQuery = it
                    onNameChange(it)
                },
                hintText = stringResource(id = R.string.input_name),
                maxLength = maxNameChar
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.name_guide1),
                    style = Typography.normal12,
                    color = Color.White
                )
                Row() {
                    if (isDuplicateName) {
                        Text(
                            text = stringResource(id = R.string.name_guide3),
                            style = Typography.normal12,
                            color = Color.Red
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.name_guide2),
                            style = Typography.normal12,
                            color = if (isValidName) Color.White else Color.Red
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "(${nameQuery.length}/6)",
                            style = Typography.normal12,
                            color = if (isValidName) Color.White else Color.Red
                        )
                    }
                }
            }
        }
    }
}
