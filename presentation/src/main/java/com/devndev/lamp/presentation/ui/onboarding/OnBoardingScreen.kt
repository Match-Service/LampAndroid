package com.devndev.lamp.presentation.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.LampButton
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    onButtonClick: () -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenHeightDp = configuration.screenHeightDp
    val topPaddingDp = screenHeightDp * 0.1f
    val topPadding = topPaddingDp.dp

    val pageCount = 4
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    val contentHeightDp = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    if (pagerState.currentPage > 0) {
        BackHandler {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LampBlack)
                .padding(top = topPadding, start = 25.dp, end = 25.dp, bottom = 20.dp)
                .clipToBounds(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(1f)
                    .background(LampBlack),
                state = pagerState
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (page) {
                        0 ->
                            Box(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        val heightPx = coordinates.size.height
                                        contentHeightDp.value = with(density) { heightPx.toDp() }
                                    }
                            ) {
                                OnBoardingBaseScreen(
                                    title = stringResource(R.string.lamp_title),
                                    msg = stringResource(R.string.lamp_msg),
                                    underlineText = "설레는 미팅",
                                    info = stringResource(R.string.lamp_info),
                                    res = R.drawable.onboarding_1
                                )
                            }

                        1 -> OnBoardingBaseScreen(
                            title = stringResource(R.string.match_title),
                            msg = stringResource(R.string.match_msg),
                            underlineText = "투표를 시작",
                            info = stringResource(R.string.match_info),
                            res = R.drawable.onboarding_2
                        )

                        2 -> OnBoardingBaseScreen(
                            title = stringResource(R.string.chat_title),
                            msg = stringResource(R.string.chat_msg),
                            underlineText = "첫 미팅을 위해 약속",
                            info = stringResource(R.string.chat_info),
                            res = R.drawable.onboarding_3
                        )

                        3 -> OnBoardingBaseScreen(
                            title = stringResource(R.string.attractive_title),
                            msg = stringResource(R.string.attractive_msg),
                            underlineText = "이성의 매력도",
                            info = stringResource(R.string.attractive_info),
                            res = R.drawable.onboarding_4
                        )
                    }
                }
            }
            if (pagerState.currentPage == 3) {
                LampButton(
                    isGradient = true,
                    buttonText = stringResource(R.string.start),
                    onClick = {
                        viewModel.saveIsNotFirstOpen {
                            onButtonClick()
                        }
                    },
                    enabled = true,
                    icon = painterResource(id = R.drawable.app_logo)
                )
            } else {
                TextButton(
                    modifier = Modifier.padding(bottom = 18.dp),
                    onClick = {
                        viewModel.saveIsNotFirstOpen {
                            onButtonClick()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.skip_onboarding),
                        style = Typography.medium15.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = Color.White
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topPadding + contentHeightDp.value),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(4.dp)
                        .background(
                            color = if (isSelected) LightGray else Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}
