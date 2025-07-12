package com.devndev.lamp.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.BackGroundColor
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.ui.alarm.AlarmViewModel
import com.devndev.lamp.presentation.ui.alarm.navigation.alarmNavGraph
import com.devndev.lamp.presentation.ui.alarm.navigation.navigateAlarm
import com.devndev.lamp.presentation.ui.assessment.navigation.assessmentListNavGraph
import com.devndev.lamp.presentation.ui.chatting.navigation.chatListNavGraph
import com.devndev.lamp.presentation.ui.chatting.navigation.navigateChatList
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.navigation.creationNavGraph
import com.devndev.lamp.presentation.ui.home.navigation.homeNavGraph
import com.devndev.lamp.presentation.ui.home.navigation.navigateHome
import com.devndev.lamp.presentation.ui.login.navigation.emailLoginNavGraph
import com.devndev.lamp.presentation.ui.login.navigation.forgotPasswordNavGraph
import com.devndev.lamp.presentation.ui.mypage.navigation.myPageNavGraph
import com.devndev.lamp.presentation.ui.mypage.navigation.navigateMyPage
import com.devndev.lamp.presentation.ui.mypage.navigation.profileEditNavGraph
import com.devndev.lamp.presentation.ui.review.navigation.reviewNavGraph
import com.devndev.lamp.presentation.ui.search.navigation.inviteNavGraph
import com.devndev.lamp.presentation.ui.search.navigation.searchNavGraph
import com.devndev.lamp.presentation.ui.signup.navigation.signUpNavGraph
import com.devndev.lamp.presentation.ui.signup.navigation.startLampNavGraph

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    signOut: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val state by viewModel.state.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    var isTopBarVisible by remember { mutableStateOf(true) }
    var isBottomBarVisible by remember { mutableStateOf(true) }

    var isNeedAlarmUpdate by remember { mutableStateOf(false) }

    LaunchedEffect(currentRoute) {
        isNeedAlarmUpdate = when (currentRoute) {
            Route.HOME,
            Route.CHAT_LIST,
            Route.MYPAGE,
            Route.SEARCH,
            Route.INVITE,
            Route.PROFILE_EDIT,
            Route.FIND -> {
                true
            }

            else -> {
                false
            }
        }
    }

    Scaffold(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                keyboardController?.hide()
            })
        },
        containerColor = BackGroundColor,
        topBar = {
            if (isTopBarVisible) {
                if (
                    currentRoute != Route.START_LAMP &&
                    currentRoute != Route.CREATION &&
                    currentRoute != Route.REVIEW &&
                    currentRoute != Route.CHAT_LIST
                ) {
                    when (currentRoute) {
                        Route.SIGNUP,
                        Route.EMAIL_LOGIN,
                        Route.FORGOT_PASSWORD,
                        Route.ASSESSMENT_LIST -> {
                            LampTopBar(
                                navController = navController,
                                isAlarmIconNeed = false,
                                needAlarmUpdate = isNeedAlarmUpdate
                            )
                        }

                        else -> {
                            LampTopBar(
                                navController = navController,
                                isAlarmIconNeed = true,
                                needAlarmUpdate = isNeedAlarmUpdate,
                                isAssessmentExist = state.isAssessmentListExist
                            ) {
                                viewModel.updateIsAssessmentExist(false)
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(0.dp))
                }
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }
        },
        bottomBar = {
            if (currentRoute != Route.SEARCH &&
                currentRoute != Route.CREATION &&
                currentRoute != Route.INVITE &&
                currentRoute != Route.ALARM &&
                currentRoute != Route.SIGNUP &&
                currentRoute != Route.START_LAMP &&
                currentRoute != Route.PROFILE_EDIT &&
                currentRoute != Route.EMAIL_LOGIN &&
                currentRoute != Route.FORGOT_PASSWORD &&
                currentRoute != Route.REVIEW
            ) {
                LampBottomNavigation(navController, LampBlack) {
                    viewModel.updateIsAssessmentExist(false)
                }
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }

            if (isBottomBarVisible) {
                LampBottomNavigation(navController, LampBlack) {
                    viewModel.updateIsAssessmentExist(false)
                }
            } else {
                LampBottomNavigation(navController, Gray) {
                    viewModel.updateIsAssessmentExist(false)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Route.HOME
        ) {
            homeNavGraph(
                padding = innerPadding,
                navController = navController,
                isTopBarVisible = isTopBarVisible,
                isBottomBarVisible = isBottomBarVisible,
                onTopBarVisibleChange = { isTopBarVisible = it },
                onBottomBarVisibleChange = { isBottomBarVisible = it },
                isAssessmentExist = {
                    viewModel.updateIsAssessmentExist(it)
                }
            )
            chatListNavGraph(padding = innerPadding, navController = navController)
            myPageNavGraph(padding = innerPadding, navController = navController, signOut = signOut)
            searchNavGraph(padding = innerPadding, navController = navController)
            inviteNavGraph(padding = innerPadding, navController = navController)
            creationNavGraph(padding = innerPadding, navController = navController)
            alarmNavGraph(padding = innerPadding, navController = navController)
            signUpNavGraph(padding = innerPadding, navController = navController)
            startLampNavGraph(navController = navController)
            profileEditNavGraph(padding = innerPadding, navController = navController)
            emailLoginNavGraph(padding = innerPadding, navController = navController)
            forgotPasswordNavGraph(padding = innerPadding, navController = navController)
            reviewNavGraph(padding = innerPadding, navController = navController)
            assessmentListNavGraph(padding = innerPadding, navController = navController)
        }
    }
}

@Composable
fun LampTopBar(
    navController: NavController,
    isAlarmIconNeed: Boolean,
    color: Color = LampBlack,
    needAlarmUpdate: Boolean,
    isChatExist: Boolean = false,
    isAssessmentExist: Boolean = false,
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    updateAssessmentExist: () -> Unit = {}
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val state by alarmViewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    if (needAlarmUpdate) {
        alarmViewModel.getAlarm()
    }
    LaunchedEffect(needAlarmUpdate) {
        if (needAlarmUpdate) {
            alarmViewModel.getAlarm()
        }
    }

    val alarmIcon = if (currentRoute == Route.ALARM || state.alarmExist) {
        painterResource(id = R.drawable.alarm_icon_on)
    } else {
        painterResource(id = R.drawable.alarm_icon)
    }

    val logoColor = if (color != LampBlack || isChatExist) {
        Color.White
    } else {
        LightGray
    }

    val backgroundColor = if (isAssessmentExist) {
        Gray
    } else {
        color
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(backgroundColor)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.app_logo),
            contentDescription = "AppLogo",
            tint = logoColor,
            modifier = Modifier
                .height(30.dp)
                .width(72.dp)
        )
        if (isAlarmIconNeed) {
            Icon(
                alarmIcon,
                contentDescription = "AlarmIcon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (currentRoute != Route.ALARM) {
                            updateAssessmentExist()
                            navController.navigateAlarm()
                        } else {
                            navController.popBackStack()
                        }
                    }
            )
        }
    }
}

@Composable
fun LampBottomNavigation(
    navController: NavController,
    containerColor: Color,
    updateAssessmentExist: () -> Unit
) {
    NavigationBar(
        containerColor = containerColor,
        contentColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val homeNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.HOME) { inclusive = true }
        }

        val chattingNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.CHAT_LIST) { inclusive = true }
        }

        val myPageNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.MYPAGE) { inclusive = true }
        }

        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = Route.HOME,
                        tint = if (currentRoute == Route.HOME) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                navController.navigateHome(homeNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
            enabled = currentRoute != Route.HOME
        )

        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.chatting),
                        contentDescription = Route.CHAT_LIST,
                        tint = if (currentRoute == Route.CHAT_LIST) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                navController.navigateChatList(chattingNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
            enabled = currentRoute != Route.CHAT_LIST
        )

        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.mypage),
                        contentDescription = Route.MYPAGE,
                        tint = if (currentRoute == Route.MYPAGE) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                updateAssessmentExist()
                navController.navigateMyPage(myPageNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
            enabled = currentRoute != Route.MYPAGE
        )
    }
}
