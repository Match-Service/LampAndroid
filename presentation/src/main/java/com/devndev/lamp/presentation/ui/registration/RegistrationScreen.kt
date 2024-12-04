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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.AlarmSetting
import com.devndev.lamp.domain.model.BioQuestion
import com.devndev.lamp.domain.model.User
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.navigation.navigateMain
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.devndev.lamp.presentation.ui.common.InstagramStep
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.common.RegistrationScreen
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography
import java.time.LocalDate

@Composable
fun RegistrationScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val logTag = "RegistrationScreen"
    val context = LocalContext.current
    val currentStep by registrationViewModel.currentStep.collectAsState()
    var name by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }
    val isDuplicateName by registrationViewModel.isDuplicateName.collectAsState()

    val currentYear = LocalDate.now().year
    var birthYear by remember { mutableStateOf(currentYear.toString()) }
    var birthMonth by remember { mutableStateOf("1") }
    var birthDay by remember { mutableStateOf("1") }

    var selectedDrink by remember { mutableStateOf("") }
    var selectedSmoke by remember { mutableStateOf("") }
    var selectedExercise by remember { mutableStateOf("") }

    var instagramId by remember { mutableStateOf("") }

    val instagramStep by registrationViewModel.instagramStep.collectAsState()
    var isAuthButtonClicked by remember { mutableStateOf(false) }

    val isSignUpSuccess by registrationViewModel.isSignUpSuccess.collectAsState()

    var bitmaps by remember { mutableStateOf(List(6) { null as Bitmap? }) }
    var profileIntro by remember { mutableStateOf("") }

    LaunchedEffect(isSignUpSuccess) {
        if (isSignUpSuccess) {
            navController.navigateMain(MainScreenPage.HOME)
        }
    }

    fun isKoreanAndEnglishOnly(name: String): Boolean {
        val regex = "^[a-zA-Z가-힣]+$".toRegex()
        if (name.isEmpty()) {
            return true
        }
        return regex.matches(name)
    }

    fun signUp() {
        val gender = if (selectedGender == "남성") {
            "MALE"
        } else {
            "FEMALE"
        }
        val cleanedYear = birthYear.replace("년", "").trim()
        val cleanedMonth = birthMonth.replace("월", "").trim()
        val cleanedDay = birthDay.replace("일", "").trim()

        val birth = "${cleanedYear.takeLast(2)}${cleanedMonth.padStart(2, '0')}${
        cleanedDay.padStart(
            2,
            '0'
        )
        }"
        val user = User(
            name = name,
            job = "STUDENT",
            jobName = "STUDENT",
            gender = gender,
            birth = birth,
            instagramId = instagramId,
            bio = profileIntro,
            profileImages = listOf(""),
            alarmSetting = AlarmSetting(
                allPush = true,
                lampInvite = true,
                newMatch = true,
                receiveBadge = true,
                receiveMessage = true
            ),
            bioQuestions = listOf(
                BioQuestion("음주", selectedDrink),
                BioQuestion("흡연", selectedSmoke),
                BioQuestion("운동", selectedExercise)
            )

        )
        registrationViewModel.signUp(user)
    }

    registrationViewModel.saveIsNeedSignOut(true)

    BackHandler(enabled = true) {
        if (currentStep > 1) {
            registrationViewModel.updateCurrentStep(currentStep - 1)
        } else {
            AuthManager.updateAccountStatus(AccountStatus.NONE)
            loginViewModel.signOut()
            registrationViewModel.saveIsNeedSignOut(false)
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
                    .height(4.dp)
                    .clip(RoundedCornerShape(50)),
                color = LightGray,
                trackColor = Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            TopNavigationBar(
                text = "",
                onBackButtonClick = {
                    if (currentStep > 1) {
                        registrationViewModel.updateCurrentStep(currentStep - 1)
                    } else {
                        AuthManager.updateAccountStatus(AccountStatus.NONE)
                        loginViewModel.signOut()
                        registrationViewModel.saveIsNeedSignOut(false)
                        navController.popBackStack()
                    }
                },
                onXButtonClick = {
                    AuthManager.updateAccountStatus(AccountStatus.NONE)
                    loginViewModel.signOut()
                    registrationViewModel.saveIsNeedSignOut(false)
                    navController.popBackStack()
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
                        RegistrationScreen.NAME -> NameScreen(
                            name = name,
                            onNameChange = { newName ->
                                name = newName
                                isNameValid = isKoreanAndEnglishOnly(newName)
                                if (isDuplicateName) {
                                    registrationViewModel.updateIsDuplicateName(false)
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
                                registrationViewModel.updateInstagramStep(InstagramStep.NONE)
                            },
                            step = instagramStep,
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
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentStep == RegistrationScreen.UNIVERSITY || currentStep == RegistrationScreen.INSTAGRAM) {
                Text(
                    modifier = Modifier.clickable {
                        registrationViewModel.updateCurrentStep(currentStep + 1)
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
                if (instagramStep == InstagramStep.VALID) {
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
                            registrationViewModel.checkIsDuplicateName(name)
                        }
                    } else if (currentStep == RegistrationScreen.BIRTH) {
                        Log.d(logTag, "$birthYear $birthMonth $birthDay")
                        registrationViewModel.updateCurrentStep(currentStep + 1)
                    } else if (currentStep == RegistrationScreen.INSTAGRAM) {
                        if (buttonText == context.getString(R.string.authentication)) {
                            registrationViewModel.checkIsValidInstagramId(instagramId)
                            isAuthButtonClicked = true
                        } else {
                            registrationViewModel.updateCurrentStep(currentStep + 1)
                        }
                    } else if (currentStep == RegistrationScreen.PROFILE) {
                        registrationViewModel.uploadImages(bitmaps)
                        signUp()
                    } else {
                        registrationViewModel.updateCurrentStep(currentStep + 1)
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
