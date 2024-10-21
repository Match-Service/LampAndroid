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
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun InstagramScreen(
    instagramID: String,
    onInstagramIDChange: (String) -> Unit,
    isValid: Boolean,
    isAuthButtonClicked: Boolean
) {
    SelectionScreen(text = stringResource(id = R.string.input_insta)) {
        var idQuery by remember { mutableStateOf(instagramID) }
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampTextField(
                width = 300,
                isGradient = !isValid && isAuthButtonClicked,
                query = idQuery,
                onQueryChange = {
                    idQuery = it
                    onInstagramIDChange(it)
                },
                hintText = stringResource(id = R.string.guide_insta)
            )

            var guideText: String
            var guideTextColor = Color.White
            if (isAuthButtonClicked) {
                if (isValid) {
                    guideText = stringResource(id = R.string.auth_success)
                } else {
                    guideText = stringResource(id = R.string.auth_failure)
                    guideTextColor = WomanColor
                }
            } else {
                guideText = stringResource(id = R.string.university_guide1)
            }
            Text(
                modifier = Modifier.width(230.dp),
                text = guideText,
                style = Typography.normal12,
                color = guideTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
