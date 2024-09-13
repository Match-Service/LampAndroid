package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun LampIntroductionScreen(
    lampName: String,
    lampSummary: String,
    onLampNameChange: (String) -> Unit,
    onLampSummaryChange: (String) -> Unit
) {
    SelectionScreen(text = stringResource(id = R.string.lame_introduction)) {
        var subjectQuery by remember { mutableStateOf(lampName) }
        var summaryQuery by remember { mutableStateOf(lampSummary) }
        val maxSubjectChar = 8
        val maxSummaryChar = 100
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .width(270.dp)
                    .background(LampBlack, shape = RoundedCornerShape(27.dp))
                    .border(
                        width = 1.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(27.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = subjectQuery,
                    onValueChange = { newText ->
                        if (newText.length <= maxSubjectChar) {
                            subjectQuery = newText
                            onLampNameChange(newText)
                        }
                    },
                    textStyle = Typography.medium18.copy(color = Color.White),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                ) { innerTextField ->
                    if (subjectQuery.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_lamp_name),
                            color = LightGray,
                            style = Typography.medium18
                        )
                    }
                    innerTextField()
                }
                IconButton(
                    modifier = Modifier.size(10.dp),
                    onClick = {
                        subjectQuery = ""
                        onLampNameChange(subjectQuery)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_button),
                        contentDescription = "검색 지우기",
                        tint = LightGray
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(120.dp)
                    .background(LampBlack, shape = RoundedCornerShape(20.dp))
                    .border(
                        width = 1.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(27.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                BasicTextField(
                    value = summaryQuery,
                    onValueChange = { newText ->
                        if (newText.length <= maxSummaryChar) {
                            summaryQuery = newText
                            onLampSummaryChange(newText)
                        }
                    },
                    textStyle = Typography.normal14.copy(color = Color.White),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier

                ) { innerTextField ->
                    if (summaryQuery.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_lamp_summary),
                            color = LightGray,
                            style = Typography.normal14
                        )
                    }
                    innerTextField()
                }
            }
        }
    }
}
