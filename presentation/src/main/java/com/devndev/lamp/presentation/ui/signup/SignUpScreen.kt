package com.devndev.lamp.presentation.ui.signup

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun SignUpScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(1) }

    var name by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }
    var isDuplicateName by remember { mutableStateOf(false) }

    fun isKoreanAndEnglishOnly(name: String): Boolean {
        val regex = "^[a-zA-Z가-힣]+$".toRegex()
        if (name.isEmpty()) {
            return true
        }
        return regex.matches(name)
    }

    // 추후 삭제
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
            .background(color = LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.app_logo),
                contentDescription = "AppLogo",
                tint = LightGray,
                modifier = Modifier.height(24.dp)
            )
        }
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
                        1 -> 0.142f
                        2 -> 0.248f
                        3 -> 0.426f
                        4 -> 0.568f
                        5 -> 0.710f
                        6 -> 0.852f
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
                    1 -> NameScreen(
                        name = name,
                        onNameChange = { newName ->
                            name = newName
                            isNameValid = isKoreanAndEnglishOnly(newName)
                            if (isDuplicateName) {
                                isDuplicateName = false
                            }
                        },
                        isValidName = isNameValid,
                        isDuplicateName = isDuplicateName
                    )

                    2 -> UniversityScreen(
                        university = university,
                        onUniversityChange = { newUniversity -> university = newUniversity }
                    )

                    3 -> GenderScreen(selectedOption = selectedGender) {
                        selectedGender = it
                    }

                    4 -> BirthScreen()
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentStep == 2) {
                Text(
                    modifier = Modifier.clickable {
                        currentStep++
                        university = ""
                    },
                    text = buildAnnotatedString {
                        append(context.getString(R.string.skip))
                        addStyle(
                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                            start = 0,
                            end = this.length
                        )
                    },
                    color = Color.White,
                    style = Typography.normal15
                )
            }
            LampButton(
                isGradient = true,
                buttonText = if (currentStep < 7) {
                    context.getString(R.string.next)
                } else {
                    context.getString(
                        R.string.done
                    )
                },
                onClick = {
                    if (currentStep == 1) {
                        isNameValid = isKoreanAndEnglishOnly(name)
                        if (isNameValid) {
                            isDuplicateName = (0..1).random() == 1
                            if (!isDuplicateName) {
                                currentStep++
                            }
                        }
                    } else {
                        currentStep++
                    }
                },
                enabled = when (currentStep) {
                    1 -> name.isNotEmpty()
                    2 -> university.isNotEmpty()
                    3 -> selectedGender.isNotEmpty()
                    else -> lampName.isNotEmpty()
                }
            )
        }
    }
}
