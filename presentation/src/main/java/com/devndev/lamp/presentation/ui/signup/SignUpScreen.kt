package com.devndev.lamp.presentation.ui.signup

import androidx.activity.compose.BackHandler
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
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.SignUpScreen
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun SignUpScreen(modifier: Modifier, navController: NavController) {
    var currentStep by remember { mutableIntStateOf(1) }

    var isFirstEssentialConsent by remember { mutableStateOf(false) }
    var isSecondEssentialConsent by remember { mutableStateOf(false) }
    var isThirdEssentialConsent by remember { mutableStateOf(false) }
    var isOptionalConsent by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var certificationNumber by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(true) }
    var isGetNumberButtonClick by remember { mutableStateOf(false) }
    var isValidCertificationNumber by remember { mutableStateOf(false) }
    var isCertifyButtonClick by remember { mutableStateOf(false) }

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
                when (currentStep) {
                    SignUpScreen.CONSENT -> ConsentScreen()
                    SignUpScreen.EMAIL -> EmailScreen(
                        email = email,
                        onEmailChange = {
                            email = it
                            certificationNumber = ""
                            isGetNumberButtonClick = false
                            isValidCertificationNumber = false
                            isCertifyButtonClick = false
                        },
                        certificationNumber = certificationNumber,
                        onCertificationNumberChange = {
                            certificationNumber = it
                            isCertifyButtonClick = false
                        },
                        isValidEmail = isValidEmail,
                        isValidCertificationNumber = isValidCertificationNumber,
                        isGetNumberButtonClick = isGetNumberButtonClick,
                        isCertifyButtonClick = isCertifyButtonClick
                    )
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
                            if (!isGetNumberButtonClick) {
                                stringResource(id = R.string.get_certification_number)
                            } else {
                                stringResource(id = R.string.certify)
                            }
                        }

                        else -> {
                            ""
                        }
                    },
                    onClick = {
                        if (currentStep == SignUpScreen.EMAIL) {
                            if (!isGetNumberButtonClick) {
                                isGetNumberButtonClick = true
                                isValidEmail = isValidEmail(email)
                            } else {
                                if (!isCertifyButtonClick) {
                                    isCertifyButtonClick = true
                                    isValidCertificationNumber = (0..1).random() == 1
                                    if (isValidCertificationNumber) {
                                        currentStep++
                                    }
                                }
                            }
                        } else {
                            currentStep++
                        }
                    },
                    enabled = true
                )
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    return email.matches(Regex(emailPattern))
}
