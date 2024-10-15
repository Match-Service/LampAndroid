package com.devndev.lamp.presentation.ui.signup

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.EmailStatus
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.PasswordStatus
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.common.SignUpScreen
import com.devndev.lamp.presentation.ui.signup.navigation.navigateStartLamp
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.Typography
import kotlinx.coroutines.delay

private const val INITIAL_TIME = 180

@Composable
fun SignUpScreen(modifier: Modifier, navController: NavController) {
    val logTag = "SignUpScreen"

    var currentStep by remember { mutableIntStateOf(1) }

    var isAllConsent by remember { mutableStateOf(false) }
    var isFirstEssentialConsent by remember { mutableStateOf(false) }
    var isSecondEssentialConsent by remember { mutableStateOf(false) }
    var isThirdEssentialConsent by remember { mutableStateOf(false) }
    var isOptionalConsent by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var certificationNumber by remember { mutableStateOf("") }
    var isValidCertificationNumber by remember { mutableStateOf(false) }
    var emailStatus by remember { mutableIntStateOf(EmailStatus.NONE) }
    var totalSeconds by remember { mutableIntStateOf(INITIAL_TIME) }
    var isTimerRunning by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordStatus by remember { mutableIntStateOf(PasswordStatus.NONE) }

    LaunchedEffect(totalSeconds == INITIAL_TIME) {
        if (emailStatus != EmailStatus.NONE && emailStatus != EmailStatus.INVALID_EMAIL) {
            emailStatus = EmailStatus.NORMAL
            isTimerRunning = true
        }
    }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && totalSeconds > 0) {
            delay(1000L)
            totalSeconds--
        }
        if (totalSeconds == 0) {
            isTimerRunning = false
        }
    }

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
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.back_arrow),
                        contentDescription = "뒤로가기",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            if (currentStep > 1) {
                                currentStep--
                            } else {
                                navController.popBackStack()
                            }
                        }
                    )
                    val stepText = when (currentStep) {
                        SignUpScreen.CONSENT -> stringResource(id = R.string.consent)
                        else -> {
                            stringResource(id = R.string.sign_up)
                        }
                    }
                    Text(
                        text = stepText,
                        style = Typography.semiBold25,
                        fontSize = 25.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.x_button_big),
                    contentDescription = "나가기",
                    tint = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

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
                        SignUpScreen.CONSENT -> {
                            ConsentScreen(
                                isAllConsent = isAllConsent,
                                isFirstEssentialConsent = isFirstEssentialConsent,
                                isSecondEssentialConsent = isSecondEssentialConsent,
                                isThirdEssentialConsent = isThirdEssentialConsent,
                                isOptionalConsent = isOptionalConsent,
                                onAllConsentChange = {
                                    isAllConsent = it
                                    isFirstEssentialConsent = it
                                    isSecondEssentialConsent = it
                                    isThirdEssentialConsent = it
                                    isOptionalConsent = it
                                },
                                onFirstEssentialConsentChange = {
                                    isFirstEssentialConsent = it
                                    if (!it) isAllConsent = false
                                },
                                onSecondEssentialConsentChange = {
                                    isSecondEssentialConsent = it
                                    if (!it) isAllConsent = false
                                },
                                onThirdEssentialConsentChange = {
                                    isThirdEssentialConsent = it
                                    if (!it) isAllConsent = false
                                },
                                onOptionalConsentChange = {
                                    isOptionalConsent = it
                                    if (!it) isAllConsent = false
                                }
                            )
                            isValidCertificationNumber = false
                        }

                        SignUpScreen.EMAIL -> EmailScreen(
                            email = email,
                            onEmailChange = {
                                email = it
                                certificationNumber = ""
                                isValidCertificationNumber = false
                                emailStatus = EmailStatus.NONE
                            },
                            certificationNumber = certificationNumber,
                            onCertificationNumberChange = {
                                certificationNumber = it
                            },
                            emailStatus = emailStatus,
                            totalSeconds = totalSeconds,
                            onResendButtonClick = {
                                totalSeconds = INITIAL_TIME
                            }
                        )

                        SignUpScreen.PASSWORD -> PasswordScreen(
                            password = password,
                            onPasswordChange = {
                                password = it
                                if (isValidPassword(password)) {
                                    passwordStatus = PasswordStatus.CONFIRM_PASSWORD
                                } else {
                                    if (password.isEmpty()) {
                                        passwordStatus = PasswordStatus.NONE
                                    } else {
                                        passwordStatus = PasswordStatus.INVALID_PASSWORD
                                    }
                                }
                            },
                            confirmPassword = confirmPassword,
                            onConfirmPasswordChange = {
                                confirmPassword = it
                                if (passwordStatus == PasswordStatus.CONFIRM_PASSWORD) {
                                    if (password == confirmPassword) {
                                        passwordStatus = PasswordStatus.SUCCESS
                                    }
                                }
                            },
                            passwordStatus = passwordStatus
                        )
                    }
                }
            }
            Box(modifier = Modifier.padding(bottom = 20.dp)) {
                LampButton(
                    isGradient = true,
                    buttonText =
                    when (currentStep) {
                        SignUpScreen.CONSENT, SignUpScreen.PASSWORD -> {
                            stringResource(id = R.string.join)
                        }

                        SignUpScreen.EMAIL -> {
                            if (emailStatus == EmailStatus.NONE || emailStatus == EmailStatus.INVALID_EMAIL) {
                                stringResource(id = R.string.get_certification_number)
                            } else {
                                stringResource(id = R.string.certify)
                            }
                        }

                        else -> {
                            stringResource(id = R.string.start)
                        }
                    },
                    onClick = {
                        if (currentStep == SignUpScreen.EMAIL) {
                            Log.d(logTag, "current Email Status == $emailStatus")
                            when (emailStatus) {
                                EmailStatus.NONE,
                                EmailStatus.INVALID_EMAIL -> {
                                    if (isValidEmail(email)) {
                                        emailStatus = EmailStatus.NORMAL
                                        if (!isTimerRunning) {
                                            isTimerRunning = true
                                        }
                                    } else {
                                        emailStatus = EmailStatus.INVALID_EMAIL
                                    }
                                }

                                EmailStatus.NORMAL -> {
                                    if (isTimerRunning) {
                                        isValidCertificationNumber = (0..1).random() == 1
                                        if (isValidCertificationNumber) {
                                            currentStep++
                                        } else {
                                            Log.d(
                                                logTag,
                                                "emailStatus == INVALID_CERTIFICATION_NUMBER"
                                            )
                                            emailStatus =
                                                EmailStatus.INVALID_CERTIFICATION_NUMBER
                                        }
                                    } else {
                                        Log.d(logTag, "emailStatus == TIME_OUT")
                                        emailStatus = EmailStatus.TIME_OUT
                                    }
                                }

                                EmailStatus.INVALID_CERTIFICATION_NUMBER -> {
                                    if (isTimerRunning) {
                                        isValidCertificationNumber = (0..1).random() == 1
                                        if (isValidCertificationNumber) {
                                            emailStatus = EmailStatus.NORMAL
                                            currentStep++
                                        } else {
                                            Log.d(
                                                logTag,
                                                "emailStatus == INVALID_CERTIFICATION_NUMBER"
                                            )
                                            emailStatus =
                                                EmailStatus.INVALID_CERTIFICATION_NUMBER
                                        }
                                    } else {
                                        Log.d(logTag, "emailStatus == TIME_OUT")
                                        emailStatus = EmailStatus.TIME_OUT
                                    }
                                }
                            }
                        } else if (currentStep == SignUpScreen.PASSWORD && passwordStatus == PasswordStatus.SUCCESS) {
                            val navOption = navOptions {
                                popUpTo(Route.START_LAMP) { inclusive = true }
                            }
                            navController.navigateStartLamp(navOption)
                        } else {
                            currentStep++
                        }
                    },
                    enabled = !(currentStep == SignUpScreen.PASSWORD && passwordStatus != PasswordStatus.SUCCESS)
                )
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    return email.matches(Regex(emailPattern))
}

fun isValidPassword(password: String): Boolean {
    return password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"))
}
