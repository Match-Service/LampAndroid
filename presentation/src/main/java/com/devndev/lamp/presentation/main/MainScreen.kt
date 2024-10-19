package com.devndev.lamp.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.navigation.mainNavGraph
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.navigation.creationNavGraph
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.login.navigation.loginNavGraph
import com.devndev.lamp.presentation.ui.notification.navigation.navigateNotification
import com.devndev.lamp.presentation.ui.notification.navigation.notificationNavGraph
import com.devndev.lamp.presentation.ui.registration.navigation.registrationNavGraph
import com.devndev.lamp.presentation.ui.search.navigation.inviteNavGraph
import com.devndev.lamp.presentation.ui.search.navigation.searchNavGraph
import com.devndev.lamp.presentation.ui.signup.navigation.signUpNavGraph
import com.devndev.lamp.presentation.ui.signup.navigation.startLampNavGraph
import com.devndev.lamp.presentation.ui.splsh.navigaion.splashNavGraph
import com.devndev.lamp.presentation.ui.theme.BackGroundColor
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val pagerState = rememberPagerState { 3 }

    LaunchedEffect(Unit) {
        loginViewModel.checkLoginStatus()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isLoggedIn by AuthManager.isLoggedIn.collectAsState()
    val isLoading by AuthManager.isLoading.collectAsState()

    Scaffold(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                keyboardController?.hide()
            })
        },
        containerColor = BackGroundColor,
        topBar = {
            if (
                currentRoute != Route.LOGIN &&
                currentRoute != Route.REGISTRATION &&
                currentRoute != Route.START_LAMP &&
                currentRoute != Route.CREATION
            ) {
                if (currentRoute == Route.SIGNUP) {
                    LampTopBar(navController = navController, isAlarmIconNeed = false)
                } else {
                    LampTopBar(navController = navController, isAlarmIconNeed = true)
                }
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }
        },
        bottomBar = {
            if (currentRoute != Route.SEARCH &&
                currentRoute != Route.CREATION &&
                currentRoute != Route.LOGIN &&
                currentRoute != Route.REGISTRATION &&
                currentRoute != Route.INVITE &&
                currentRoute != Route.NOTIFICATION &&
                currentRoute != Route.SIGNUP &&
                currentRoute != Route.START_LAMP
            ) {
                LampBottomNavigation(pagerState)
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = if (isLoading) Route.SPLASH else if (isLoggedIn) Route.MAIN else Route.LOGIN
        ) {
            mainNavGraph(
                padding = innerPadding,
                navController = navController,
                pagerState = pagerState
            )
            searchNavGraph(padding = innerPadding, navController = navController)
            inviteNavGraph(padding = innerPadding, navController = navController)
            creationNavGraph(padding = innerPadding, navController = navController)
            splashNavGraph(padding = PaddingValues())
            loginNavGraph(padding = PaddingValues(), navController = navController)
            registrationNavGraph(padding = innerPadding, navController = navController)
            notificationNavGraph(padding = innerPadding, navController = navController)
            signUpNavGraph(padding = innerPadding, navController = navController)
            startLampNavGraph(navController = navController)
        }
    }
}

@Composable
fun LampTopBar(navController: NavController, isAlarmIconNeed: Boolean) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(id = R.drawable.app_logo),
            contentDescription = "AppLogo",
            tint = Gray3,
            modifier = Modifier.height(24.dp)
        )
        if (isAlarmIconNeed) {
            Icon(
                painterResource(id = R.drawable.alarm_icon),
                contentDescription = "AlarmIcon",
                tint = Gray3,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (currentRoute != Route.NOTIFICATION) {
                            navController.navigateNotification()
                        } else {
                            navController.popBackStack()
                        }
                    }
            )
        }
    }
}

@Composable
fun LampBottomNavigation(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    NavigationBar(
        containerColor = LampBlack,
        contentColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = Route.HOME,
                        tint = if (pagerState.currentPage == MainScreenPage.HOME) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(MainScreenPage.HOME)
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )

        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.chatting),
                        contentDescription = Route.CHATTING,
                        tint = if (pagerState.currentPage == MainScreenPage.CHATTING) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(MainScreenPage.CHATTING)
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )

        NavigationBarItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.mypage),
                        contentDescription = Route.MYPAGE,
                        tint = if (pagerState.currentPage == MainScreenPage.MY_PAGE) Color.White else LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(MainScreenPage.MY_PAGE)
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
    }
}
