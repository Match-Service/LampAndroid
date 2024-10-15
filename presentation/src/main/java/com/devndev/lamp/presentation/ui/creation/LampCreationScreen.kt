package com.devndev.lamp.presentation.ui.creation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.TempDB
import com.devndev.lamp.presentation.main.navigation.navigateMain
import com.devndev.lamp.presentation.ui.common.CreationScreen
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.home.TempStatus
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray

@Composable
fun LampCreationScreen(modifier: Modifier, navController: NavController) {
    var currentStep by remember { mutableIntStateOf(1) }

    var selectedPersonnel by remember { mutableStateOf(TempDB.personnel) }
    var selectedRegion by remember { mutableStateOf(TempDB.region) }
    var selectedMood by remember { mutableStateOf(TempDB.mood) }
    var lampName by remember { mutableStateOf(TempDB.lampName) }
    var lampSummary by remember { mutableStateOf(TempDB.lampSummary) }

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
            .background(LampBlack),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        CreationScreen.PERSONNEL -> 0.25f
                        CreationScreen.REGION -> 0.50f
                        CreationScreen.MOOD -> 0.75f
                        else -> 1f
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth },
                                animationSpec = tween(durationMillis = 300)
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = 300)
                                )
                            )
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> -fullWidth },
                                animationSpec = tween(durationMillis = 300)
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(durationMillis = 300)
                                )
                            )
                        }
                    },
                    label = ""
                ) { step ->
                    when (step) {
                        CreationScreen.PERSONNEL -> PersonnelScreen(selectedOption = selectedPersonnel) {
                            selectedPersonnel = it
                        }

                        CreationScreen.REGION -> RegionScreen(selectedOption = selectedRegion) {
                            selectedRegion = it
                        }

                        CreationScreen.MOOD ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clipToBounds()
                            ) {
                                MoodScreen(selectedOption = selectedMood) {
                                    selectedMood = it
                                }
                            }

                        CreationScreen.INTRODUCTION -> LampIntroductionScreen(
                            lampName = lampName,
                            lampSummary = lampSummary,
                            onLampNameChange = { newLampName -> lampName = newLampName },
                            onLampSummaryChange = { newLampSummary ->
                                lampSummary = newLampSummary
                            }
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.padding(bottom = 20.dp, start = 16.dp, end = 16.dp)) {
            LampButton(
                isGradient = true,
                buttonText = if (currentStep < 4) {
                    stringResource(id = R.string.next)
                } else {
                    stringResource(id = R.string.done)
                },
                onClick = {
                    if (currentStep < 4) {
                        currentStep++
                    } else {
                        TempDB.personnel = selectedPersonnel
                        TempDB.region = selectedRegion
                        TempDB.mood = selectedMood
                        TempDB.lampName = lampName
                        TempDB.lampSummary = lampSummary
                        TempStatus.updateIsMatching(true)
                        navController.navigateMain(MainScreenPage.HOME)
                    }
                },
                enabled = when (currentStep) {
                    CreationScreen.PERSONNEL -> selectedPersonnel.isNotEmpty()
                    CreationScreen.REGION -> selectedRegion.isNotEmpty()
                    CreationScreen.MOOD -> {
                        when (selectedMood) {
                            0 -> false
                            else -> true
                        }
                    }

                    else -> lampName.isNotEmpty() && lampSummary.isNotEmpty()
                }
            )
        }
    }
}
