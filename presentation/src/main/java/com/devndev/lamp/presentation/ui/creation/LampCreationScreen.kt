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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.ui.common.CreationScreen
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.home.navigation.navigateHome

@Composable
fun LampCreationScreen(
    lampCreationViewModel: LampCreationViewModel = hiltViewModel(),
    isEdit: Boolean,
    modifier: Modifier,
    navController: NavController
) {
    val state by lampCreationViewModel.uiState.collectAsStateWithLifecycle()

    var currentStep by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        if (isEdit) {
            lampCreationViewModel.getMyLamp()
        }
    }

    BackHandler(enabled = true) {
        if (currentStep > 1) {
            currentStep--
        } else {
            navController.navigateHome()
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
            Spacer(modifier = Modifier.height(30.dp))
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
                    .height(4.dp)
                    .clip(RoundedCornerShape(50)),
                color = LightGray,
                trackColor = Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            TopNavigationBar(
                text = "",
                horizontalPadding = 16,
                onBackButtonClick = {
                    if (currentStep > 1) {
                        currentStep--
                    } else {
                        navController.navigateHome()
                    }
                },
                onXButtonClick = {
                    navController.navigateHome()
                }
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
                        CreationScreen.PERSONNEL -> PersonnelScreen(selectedOption = state.personnel) {
                            lampCreationViewModel.updatePersonnel(it)
                        }

                        CreationScreen.REGION -> RegionScreen(selectedOption = state.region) {
                            lampCreationViewModel.updateRegion(it)
                        }

                        CreationScreen.MOOD ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clipToBounds()
                            ) {
                                MoodScreen(selectedOption = state.mood) {
                                    lampCreationViewModel.updateMood(it)
                                }
                            }

                        CreationScreen.INTRODUCTION -> LampIntroductionScreen(
                            lampName = state.lampName,
                            lampSummary = state.lampSummary,
                            onLampNameChange = { newLampName ->
                                lampCreationViewModel.updateLampName(
                                    newLampName
                                )
                            },
                            onLampSummaryChange = { newLampSummary ->
                                lampCreationViewModel.updateLampSummary(
                                    newLampSummary
                                )
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
                    if (isEdit) {
                        stringResource(id = R.string.lamp_edit_btn)
                    } else {
                        stringResource(id = R.string.lamp_crate_btn)
                    }
                },
                onClick = {
                    if (currentStep < 4) {
                        currentStep++
                    } else {
                        val hopeMatchNumber = state.personnel.split(":")[0].toInt()
                        if (isEdit) {
                            lampCreationViewModel.editLamp(
                                CreateLampParam(
                                    name = state.lampName,
                                    description = state.lampSummary,
                                    hopeMatchNumber = hopeMatchNumber,
                                    location = convertLocation(state.region),
                                    color = convertMood(state.mood)
                                )
                            ) {
                                navController.navigateHome()
                            }
                        } else {
                            lampCreationViewModel.createLamp(
                                CreateLampParam(
                                    name = state.lampName,
                                    description = state.lampSummary,
                                    hopeMatchNumber = hopeMatchNumber,
                                    location = convertLocation(state.region),
                                    color = convertMood(state.mood)
                                )
                            ) {
                                navController.navigateHome()
                            }
                        }
                    }
                },
                enabled = when (currentStep) {
                    CreationScreen.PERSONNEL -> state.personnel.isNotEmpty()
                    CreationScreen.REGION -> state.region.isNotEmpty()
                    CreationScreen.MOOD -> {
                        when (state.mood) {
                            0 -> false
                            else -> true
                        }
                    }

                    else -> state.lampName.isNotEmpty() && state.lampSummary.isNotEmpty()
                }
            )
        }
    }
}

fun convertLocation(selectedRegion: String): String {
    return when (selectedRegion) {
        "건대·성수" -> "KONDA_SEOUNGSU"
        "신촌·홍대" -> "SINCHON_HONGDAE"
        "강남·잠실" -> "GANGNAM_JAMSIL"
        "인천" -> "INCHEON"
        "경기" -> "GYEONGGI"
        else -> ""
    }
}

fun convertMood(selectedMood: Int): String {
    return when (selectedMood) {
        1 -> "FUNNY"
        2 -> "CASUAL"
        3 -> "SERIOUS"
        else -> ""
    }
}
