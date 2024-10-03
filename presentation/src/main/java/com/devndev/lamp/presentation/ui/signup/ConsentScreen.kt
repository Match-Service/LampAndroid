package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.CheckButton
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun ConsentScreen(
    isAllConsent: Boolean,
    isFirstEssentialConsent: Boolean,
    isSecondEssentialConsent: Boolean,
    isThirdEssentialConsent: Boolean,
    isOptionalConsent: Boolean,
    onAllConsentChange: (Boolean) -> Unit,
    onFirstEssentialConsentChange: (Boolean) -> Unit,
    onSecondEssentialConsentChange: (Boolean) -> Unit,
    onThirdEssentialConsentChange: (Boolean) -> Unit,
    onOptionalConsentChange: (Boolean) -> Unit
) {
    SelectionScreen(text = "") {
        Column(
            modifier = Modifier.width(300.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ConsentItem(
                text = stringResource(id = R.string.consent_all),
                size = 18,
                isConsent = isAllConsent,
                onConsentChange = { onAllConsentChange(it) }
            )

            Column(
                modifier = Modifier.padding(start = 18.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                ConsentItem(
                    text = stringResource(id = R.string.first_essential_consent),
                    size = 14,
                    isConsent = isFirstEssentialConsent,
                    onConsentChange = { onFirstEssentialConsentChange(it) }
                )
                ConsentItemWithButton(
                    text = stringResource(id = R.string.second_essential_consent),
                    isConsent = isSecondEssentialConsent,
                    onConsentChange = { onSecondEssentialConsentChange(it) },
                    onSeeButtonClick = { /*todo 팝업 표시*/ }
                )
                ConsentItemWithButton(
                    text = stringResource(id = R.string.third_essential_consent),
                    isConsent = isThirdEssentialConsent,
                    onConsentChange = { onThirdEssentialConsentChange(it) },
                    onSeeButtonClick = { /*todo 팝업 표시*/ }
                )
                ConsentItemWithButton(
                    text = stringResource(id = R.string.optional_consent),
                    isConsent = isOptionalConsent,
                    onConsentChange = { onOptionalConsentChange(it) },
                    onSeeButtonClick = { /*todo 팝업 표시*/ }
                )
            }
        }
    }
}

@Composable
fun ConsentItem(
    text: String,
    size: Int,
    isConsent: Boolean,
    onConsentChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onConsentChange(!isConsent)
        },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = if (size == 18) {
            Typography.medium18
        } else {
            Typography.normal14
        }

        CheckButton(size = size, selected = isConsent, onClick = { onConsentChange(!isConsent) })
        Text(
            text = text,
            color = Color.White,
            style = textStyle
        )
    }
}

@Composable
fun ConsentItemWithButton(
    text: String,
    isConsent: Boolean,
    onConsentChange: (Boolean) -> Unit,
    onSeeButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ConsentItem(
            text = text,
            size = 14,
            isConsent = isConsent,
            onConsentChange = { onConsentChange(it) }
        )
        Text(
            modifier = Modifier.clickable {
                onSeeButtonClick()
            },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.see_consent))
                addStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                    start = 0,
                    end = this.length
                )
            },
            color = Color.White,
            style = Typography.normal12
        )
    }
}
