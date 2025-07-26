package com.devndev.lamp.presentation.ui.review

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.assessment.AssessmentParam
import com.devndev.lamp.domain.model.assessment.AssessmentUserInfo
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.assessment.AssessmentListViewModel
import com.devndev.lamp.presentation.ui.common.ReviewScreen
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.home.navigation.navigateHome

@Composable
fun ReviewScreen(
    modifier: Modifier,
    navController: NavController,
    lampMatchId: Int,
    viewModel: AssessmentListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAssessment(lampMatchId)
    }

    val userAnswers = mutableListOf<AssessmentUserInfo>()
    val userScores = remember { mutableStateListOf<List<MutableState<Int>>>() }
    val isSkippedList = remember { mutableStateListOf<Boolean>() }

    LaunchedEffect(state.assessment?.users) {
        state.assessment?.users?.let { users ->
            users.forEach { _ ->
                userScores.add(
                    listOf(
                        mutableIntStateOf(1),
                        mutableIntStateOf(1),
                        mutableIntStateOf(1),
                        mutableIntStateOf(1)
                    )
                )
                isSkippedList.add(false)
            }
        }
    }

    var currentStep by remember { mutableIntStateOf(0) }
    var personalStep by remember { mutableIntStateOf(1) }

    var lampScore by remember { mutableStateOf<Int?>(1) }

    BackHandler {
        if (currentStep == 0) {
            navController.popBackStack()
        } else {
            if (personalStep == 1) {
                currentStep--
            } else {
                personalStep--
            }
        }
    }

    val profileSize = state.getUserSize()
    val indicatorSize = 1f / (profileSize + 1)

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
                        ReviewScreen.LAMP -> indicatorSize
                        ReviewScreen.PERSONAL -> {
                            when (personalStep) {
                                1 -> indicatorSize * 2f
                                2 -> indicatorSize * 3f
                                3 -> indicatorSize * 4f
                                4 -> indicatorSize * 5f
                                else -> 1f
                            }
                        }

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
            TopNavigationBar(
                text = "",
                onBackButtonClick = {
                    if (currentStep == 0) {
                        navController.popBackStack()
                    } else {
                        if (personalStep == 1) {
                            currentStep--
                        } else {
                            personalStep--
                        }
                    }
                },
                onXButtonClick = { navController.popBackStack() }
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
                        ReviewScreen.LAMP -> state.assessment?.let {
                            LampReviewScreen(it, lampScore ?: 1) {
                                lampScore = it
                            }
                        }

                        ReviewScreen.PERSONAL -> state.assessment?.let {
                            PersonalReviewScreen(
                                step = personalStep,
                                assessment = it,
                                userScores = userScores,
                                onProgressChange = { user, attractive, score ->
                                    userScores[user][attractive].value = score
                                }
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (currentStep == ReviewScreen.LAMP) {
                            currentStep = ReviewScreen.PERSONAL
                            lampScore = null
                        } else if (currentStep == ReviewScreen.PERSONAL) {
                            isSkippedList[personalStep - 1] = true
                            if (personalStep == profileSize) {
                                for (i in isSkippedList.indices) {
                                    if (!isSkippedList[i]) {
                                        val userId = state.assessment!!.users[i].userId
                                        userAnswers.add(AssessmentUserInfo("PERSONALITY", userScores[i][0].value, userId))
                                        userAnswers.add(AssessmentUserInfo("VOICE", userScores[i][1].value, userId))
                                        userAnswers.add(AssessmentUserInfo("FASHION", userScores[i][2].value, userId))
                                        userAnswers.add(AssessmentUserInfo("CONVERSATION", userScores[i][3].value, userId))
                                    }
                                }
                                viewModel.assessment(
                                    AssessmentParam(
                                        score = lampScore,
                                        lampId = state.assessment!!.lampId,
                                        lampMatchId = lampMatchId,
                                        lampAssessmentUserInfos = userAnswers
                                    )
                                )
                                navController.navigateHome()
                            } else {
                                personalStep++
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(LightGray),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(
                        text = "넘어가기",
                        style = Typography.medium18
                    )
                }
                Button(
                    onClick = {
                        if (currentStep == ReviewScreen.LAMP) {
                            currentStep = ReviewScreen.PERSONAL
                        } else if (currentStep == ReviewScreen.PERSONAL) {
                            isSkippedList[personalStep - 1] = false
                            if (personalStep == profileSize) {
                                for (i in isSkippedList.indices) {
                                    if (!isSkippedList[i]) {
                                        val userId = state.assessment!!.users[i].userId
                                        userAnswers.add(AssessmentUserInfo("PERSONALITY", userScores[i][0].value, userId))
                                        userAnswers.add(AssessmentUserInfo("VOICE", userScores[i][1].value, userId))
                                        userAnswers.add(AssessmentUserInfo("FASHION", userScores[i][2].value, userId))
                                        userAnswers.add(AssessmentUserInfo("CONVERSATION", userScores[i][3].value, userId))
                                    }
                                }
                                viewModel.assessment(
                                    AssessmentParam(
                                        score = lampScore,
                                        lampId = state.assessment!!.lampId,
                                        lampMatchId = lampMatchId,
                                        lampAssessmentUserInfos = userAnswers
                                    )
                                )
                                navController.navigateHome()
                            } else {
                                personalStep++
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(WomanColor, ManColor)
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(
                        text = if (currentStep == ReviewScreen.LAMP) {
                            "기록하기"
                        } else {
                            "매력 평가하기"
                        },
                        style = Typography.medium18
                    )
                }
            }
        }
    }
}
