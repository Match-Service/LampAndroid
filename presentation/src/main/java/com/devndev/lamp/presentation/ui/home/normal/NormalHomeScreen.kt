package com.devndev.lamp.presentation.ui.home.normal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.assessment.navigation.navigateAssessmentList
import com.devndev.lamp.presentation.ui.common.LampButtonWithIcon
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.home.main.HomeTextArea
import com.devndev.lamp.presentation.ui.home.normal.viewmodel.NormalHomeViewModel
import com.devndev.lamp.presentation.ui.search.navigation.navigateSearch
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NormalHomeScreen(
    viewModel: NormalHomeViewModel = hiltViewModel(),
    modifier: Modifier,
    isAssessmentExist: (Boolean) -> Unit,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.assessmentList) {
        isAssessmentExist(state.getIsNeedAssessTopBar())
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            isAssessmentExist(false)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            ) {
                HomeTextArea(
                    nameText = "${state.myInfo?.name ?: ""} " + stringResource(id = R.string.sir),
                    middleText = stringResource(id = R.string.main_header),
                    bottomText = stringResource(id = R.string.meet_with_lamp),
                    gender = state.myInfo?.gender ?: "MALE"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                val navOption = navOptions {
                    launchSingleTop = true
                }
                LampButtonWithIcon(
                    isGradient = true,
                    buttonText = stringResource(id = R.string.make_lamp),
                    guideButtonText = stringResource(id = R.string.guide_make_lamp),
                    onClick = {
                        navController.navigateCreation(isEdit = false, navOptions = navOption)
                    },
                    icon = painterResource(id = R.drawable.arrow),
                    enabled = true,
                    onIconClick = {
                        isAssessmentExist(false)
                        navController.navigateCreation(isEdit = false, navOptions = navOption)
                    }
                )
                LampButtonWithIcon(
                    isGradient = false,
                    buttonText = stringResource(id = R.string.find_friend),
                    guideButtonText = stringResource(id = R.string.guide_find_friend),
                    onClick = {
                        if (state.getIsNeedAssessTopBar()) {
                            isAssessmentExist(false)
                            coroutineScope.launch {
                                delay(50)
                            }
                        }
                        navController.navigateSearch(navOption)
                    },
                    icon = painterResource(id = R.drawable.arrow),
                    enabled = true,
                    onIconClick = {
                        if (state.getIsNeedAssessTopBar()) {
                            isAssessmentExist(false)
                            coroutineScope.launch {
                                delay(50)
                            }
                        }
                        navController.navigateSearch(navOption)
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
            )
        }
        if (state.getIsNeedAssessTopBar()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .background(Gray)
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 72.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isAssessmentExist(false)
                        navController.navigateAssessmentList()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = null,
                        tint = Color.White
                    )
                    // TODO::SHKIM assessment를 통해 text 표시
                    Text(
                        text = "7월 6일,",
                        color = Color.White,
                        style = Typography.medium15
                    )
                    Text(
                        text = "그룹명입니다 램프와 만남은 어땠나요?",
                        color = Color.White,
                        style = Typography.medium15
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.White
                )
            }
        }
    }
}
