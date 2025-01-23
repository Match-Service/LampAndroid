package com.devndev.lamp.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen

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
                query = universityQuery,
                onQueryChange = {
                    universityQuery = it
                    onUniversityChange(it)
                },
                hintText = stringResource(id = R.string.input_university)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    val fullText = stringResource(id = R.string.university_guide1)
                    val skipText = stringResource(id = R.string.skip)

                    val startIndex = fullText.indexOf(skipText)
                    val endIndex = startIndex + skipText.length

                    append(fullText)

                    addStyle(
                        style = SpanStyle(textDecoration = TextDecoration.Underline),
                        start = startIndex,
                        end = endIndex
                    )
                },
                style = Typography.normal12,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
