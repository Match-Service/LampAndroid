package com.devndev.lamp.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun UniversityScreen(
    university: String,
    onUniversityChange: (String) -> Unit
) {
    SelectionScreen(text = stringResource(id = R.string.registration_university)) {
        var universityQuery by remember { mutableStateOf(university) }
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampTextField(
                width = 270,
                isGradient = false,
                query = universityQuery,
                onQueryChange = {
                    universityQuery = it
                    onUniversityChange(it)
                },
                hintText = stringResource(id = R.string.input_university)
            )

            Text(
                modifier = Modifier.width(230.dp),
                text = stringResource(id = R.string.university_guide1),
                style = Typography.normal12,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
