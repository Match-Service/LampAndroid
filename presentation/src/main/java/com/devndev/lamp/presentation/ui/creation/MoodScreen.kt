package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R

@Composable
fun MoodScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val context = LocalContext.current
    val options = listOf("따뜻한 분위기", "신나는 분위기", "차분한 분위기")
    SelectionScreen(
        text = context.getString(R.string.select_mood)
    ) {
        // 임시 코드 여기 분위기 선택은 이 영역에 수정하시면 됩니다 @이어진.
        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { option ->
            OptionButton(
                optionText = option,
                isSelected = selectedOption == option,
                onSelect = { onSelectOption(option) }
            )
        }
    }
}
