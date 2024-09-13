package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun GenderScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val options = listOf("남성", "여성")
    SelectionScreen(text = stringResource(id = R.string.input_gender)) {
        Spacer(modifier = Modifier.height(24.dp))
        options.forEach { option ->
            val isSelected = selectedOption == option
            Button(
                onClick = { onSelectOption(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.White else Gray,
                    contentColor = if (isSelected) LampBlack else Color.White
                ),
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(52.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text(text = option, style = Typography.medium15)
            }
        }
    }
}
