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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
            buttonText = if (currentStep < 4) {
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
