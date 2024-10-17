package com.devndev.lamp.presentation.ui.registration

import android.graphics.Bitmap
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.RegistrationScreen
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun RegistrationScreen(modifier: Modifier, navController: NavController) {
    val logTag = "RegistrationScreen"
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(1) }

    var name by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }
    var isDuplicateName by remember { mutableStateOf(false) }

    var birthYear by remember { mutableStateOf("2000") }
    var birthMonth by remember { mutableStateOf("1") }
    var birthDay by remember { mutableStateOf("1") }

    var selectedDrink by remember { mutableStateOf("") }
    var selectedSmoke by remember { mutableStateOf("") }
    var selectedExercise by remember { mutableStateOf("") }

    var instagramId by remember { mutableStateOf("") }
    var isValidInstagramId by remember { mutableStateOf(false) }
    var isAuthButtonClicked by remember { mutableStateOf(false) }

    var bitmaps by remember { mutableStateOf(List(6) { null as Bitmap? }) }
    var profileIntro by remember { mutableStateOf("") }

    fun isKoreanAndEnglishOnly(name: String): Boolean {
        val regex = "^[a-zA-Z가-힣]+$".toRegex()
        if (name.isEmpty()) {
            return true
        }
        return regex.matches(name)
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
            .background(color = LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            LinearProgressIndicator(
                progress = {
                    when (currentStep) {
                        RegistrationScreen.NAME -> 0.142f
                        RegistrationScreen.UNIVERSITY -> 0.248f
                        RegistrationScreen.GENDER -> 0.426f
                        RegistrationScreen.BIRTH -> 0.568f
                        RegistrationScreen.INFO -> 0.710f
                        RegistrationScreen.INSTAGRAM -> 0.852f
                        else -> 1f
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = LightGray,
                trackColor = Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            navController.popBackStack()
                        }
                    },
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "뒤로가기",
                    tint = Color.White
                )

                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    painter = painterResource(id = R.drawable.x_button_big),
                    contentDescription = "나가기",
                    tint = Color.White
                )
            }
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
                        RegistrationScreen.NAME -> NameScreen(
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

                        RegistrationScreen.UNIVERSITY -> UniversityScreen(
                            university = university,
                            onUniversityChange = { newUniversity -> university = newUniversity }
                        )

                        RegistrationScreen.GENDER -> GenderScreen(selectedOption = selectedGender) {
                            selectedGender = it
                        }

                        RegistrationScreen.BIRTH -> BirthScreen(
                            isMan = selectedGender == stringResource(id = R.string.man),
                            selectedYear = birthYear,
                            selectedMonth = birthMonth,
                            selectedDay = birthDay,
                            onYearChange = { birthYear = it },
                            onMonthChange = { birthMonth = it },
                            onDayChange = { birthDay = it }
                        )

                        RegistrationScreen.INFO -> InfoScreen(
                            selectedDrinkOption = selectedDrink,
                            selectedSmokeOption = selectedSmoke,
                            selectedExerciseOption = selectedExercise,
                            onSelectDrinkOption = {
                                selectedDrink = it
                                Log.d(logTag, "selectedDrinkOption $selectedDrink")
                            },
                            onSelectSmokeOption = { selectedSmoke = it },
                            onSelectExerciseOption = { selectedExercise = it }
                        )

                        RegistrationScreen.INSTAGRAM -> InstagramScreen(
                            instagramID = instagramId,
                            onInstagramIDChange = {
                                instagramId = it
                                isAuthButtonClicked = false
                                isValidInstagramId = false
                            },
                            isValid = isValidInstagramId,
                            isAuthButtonClicked = isAuthButtonClicked
                        )

                        RegistrationScreen.PROFILE -> {
                            ProfileScreen(
                                profileIntro = profileIntro,
                                bitmaps = bitmaps,
                                onProfileIntroChange = { profileIntro = it },
                                onBitmapsChange = { updatedBitmaps -> bitmaps = updatedBitmaps }
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentStep == RegistrationScreen.UNIVERSITY || currentStep == RegistrationScreen.INSTAGRAM) {
                Text(
                    modifier = Modifier.clickable {
                        currentStep++
                        university = ""
                    },
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.skip))
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
            val buttonText = if (currentStep == RegistrationScreen.INSTAGRAM) {
                if (isValidInstagramId) {
                    stringResource(id = R.string.next)
                } else {
                    stringResource(id = R.string.authentication)
                }
            } else if (currentStep < RegistrationScreen.PROFILE) {
                stringResource(id = R.string.next)
            } else {
                stringResource(id = R.string.start)
            }
            val icon = if (currentStep == RegistrationScreen.PROFILE) {
                painterResource(id = R.drawable.app_logo)
            } else {
                null
            }
            LampButton(
                isGradient = true,
                buttonText = buttonText,
                onClick = {
                    if (currentStep == RegistrationScreen.NAME) {
                        isNameValid = isKoreanAndEnglishOnly(name)
                        if (isNameValid) {
                            isDuplicateName = (0..1).random() == 1
                            if (!isDuplicateName) {
                                currentStep++
                            }
                        }
                    } else if (currentStep == RegistrationScreen.BIRTH) {
                        Log.d(logTag, "$birthYear $birthMonth $birthDay")
                        currentStep++
                    } else if (currentStep == RegistrationScreen.INSTAGRAM) {
                        if (buttonText == context.getString(R.string.authentication)) {
                            isValidInstagramId = (0..1).random() == 1
                            isAuthButtonClicked = true
                        } else {
                            currentStep++
                        }
                    } else {
                        currentStep++
                    }
                },
                enabled = when (currentStep) {
                    RegistrationScreen.NAME -> name.isNotEmpty()
                    RegistrationScreen.UNIVERSITY -> university.isNotEmpty()
                    RegistrationScreen.GENDER -> selectedGender.isNotEmpty()
                    RegistrationScreen.BIRTH -> true
                    RegistrationScreen.INFO -> selectedDrink.isNotEmpty() && selectedSmoke.isNotEmpty() && selectedExercise.isNotEmpty()
                    RegistrationScreen.INSTAGRAM -> instagramId.isNotEmpty()
                    RegistrationScreen.PROFILE -> bitmaps[0] != null && profileIntro.isNotEmpty()
                    else -> false
                },
                icon = icon
            )
        }
    }
}
