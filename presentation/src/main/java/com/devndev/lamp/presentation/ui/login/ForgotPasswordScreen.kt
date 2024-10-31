package com.devndev.lamp.presentation.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.ForgotPasswordScreen
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.theme.LampBlack

@Composable
fun ForgotPasswordScreen(modifier: Modifier, navController: NavController) {
    var currentStep by remember { mutableIntStateOf(0) }

    var email by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TopNavigationBar(
                text = stringResource(id = R.string.change_password),
                onBackButtonClick = { navController.popBackStack() },
                onXButtonClick = { navController.popBackStack() }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
                        ForgotPasswordScreen.INFO_INPUT -> {
                            InfoInputScreen(
                                email = email,
                                onEmailChange = { email = it },
                                birth = birth,
                                onBirthChange = { birth = it },
                                selectedOption = gender,
                                onSelectOption = { gender = it }
                            )
                        }
//
//                        ForgotPasswordScreen.AUTH -> {
//
//                        }
//
//                        ForgotPasswordScreen.CHANGE_PASSWORD -> {
//
//                        }
                    }
                }
            }
        }
        val buttonText = when (currentStep) {
            ForgotPasswordScreen.INFO_INPUT -> {
                stringResource(id = R.string.get_certification_number)
            }

            ForgotPasswordScreen.AUTH -> {
                stringResource(id = R.string.authentication)
            }

            ForgotPasswordScreen.CHANGE_PASSWORD -> {
                stringResource(id = R.string.change_password)
            }

            else -> ""
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampButton(
                isGradient = true,
                buttonText = buttonText,
                onClick = {},
                enabled = when (currentStep) {
                    ForgotPasswordScreen.INFO_INPUT -> {
                        email.isNotEmpty() && birth.isNotEmpty() && gender.isNotEmpty()
                    }

                    ForgotPasswordScreen.AUTH -> {
                        true
                    }

                    ForgotPasswordScreen.CHANGE_PASSWORD -> {
                        true
                    }

                    else -> true
                }
            )
        }
    }
}
