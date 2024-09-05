package com.devndev.lamp.presentation.ui.creation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun LampCreationScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(1) }

    var selectedPersonnel by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("") }
    var lampName by remember { mutableStateOf("") }
    var lampSummary by remember { mutableStateOf("") }

    BackHandler(enabled = true) {
        if (currentStep > 1) {
            currentStep--
        } else {
            navController.popBackStack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "뒤로가기",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_button_big),
                        contentDescription = "나가기",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(
                progress = {
                    when (currentStep) {
                        1 -> 0.25f
                        2 -> 0.50f
                        3 -> 0.75f
                        else -> 1f
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = LightGray,
                trackColor = Gray
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (currentStep) {
                    1 -> PersonnelScreen(selectedOption = selectedPersonnel) {
                        selectedPersonnel = it
                    }

                    2 -> RegionScreen(selectedOption = selectedRegion) {
                        selectedRegion = it
                    }

                    3 -> MoodScreen(selectedOption = selectedMood) {
                        selectedMood = it
                    }
                }
            }
        }

        LampButton(
            isGradient = true,
            buttonText = if (currentStep < 3) {
                context.getString(R.string.next)
            } else {
                context.getString(
                    R.string.done
                )
            },
            onClick = { currentStep++ },
            enabled = when (currentStep) {
                1 -> selectedPersonnel.isNotEmpty()
                2 -> selectedRegion.isNotEmpty()
                else -> selectedMood.isNotEmpty()
            }
        )
    }
}

@Composable
fun PersonnelScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.select_personnel),
                color = Color.White,
                style = Typography.semiBold25,
                lineHeight = 33.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            val options = listOf("2:2", "3:3", "4:4", "5:5")

            options.forEach { option ->
                OptionButton(
                    optionText = option,
                    isSelected = selectedOption == option,
                    onSelect = { onSelectOption(option) }
                )
            }
        }
    }
}

@Composable
fun RegionScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
    ) {
        Column {
            Text(text = "램프 인원과 함께하고 싶은 지역을 선택해 주세요")
            Spacer(modifier = Modifier.height(24.dp))

            // 선택 가능한 옵션 리스트
            val options = listOf("서울", "경기도", "인천", "부산")

            options.forEach { option ->
                OptionButton(
                    optionText = option,
                    isSelected = selectedOption == option,
                    onSelect = { onSelectOption(option) }
                )
            }
        }
    }
}

@Composable
fun MoodScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    Column {
        Text(text = "어떤 색의 램프를 켜볼까요?")
        Spacer(modifier = Modifier.height(16.dp))

        // 선택 가능한 옵션 리스트
        val options = listOf("따뜻한 분위기", "신나는 분위기", "차분한 분위기")

        options.forEach { option ->
            OptionButton(
                optionText = option,
                isSelected = selectedOption == option,
                onSelect = { onSelectOption(option) }
            )
        }
    }
}

@Composable
fun OptionButton(optionText: String, isSelected: Boolean, onSelect: () -> Unit) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Gray,
            contentColor = if (isSelected) Color.Black else Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(bottom = 4.dp)
    ) {
        Text(text = optionText, style = Typography.medium15)
    }
}

@Preview
@Composable
fun ABC() {
    LampCreationScreen(modifier = Modifier, navController = rememberNavController())
}
