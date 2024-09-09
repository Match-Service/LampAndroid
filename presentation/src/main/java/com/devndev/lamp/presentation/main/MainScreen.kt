package com.devndev.lamp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.chatting.navigation.chattingNavGraph
import com.devndev.lamp.presentation.ui.chatting.navigation.navigateChatting
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.navigation.creationNavGraph
import com.devndev.lamp.presentation.ui.home.navigaion.homeNavGraph
import com.devndev.lamp.presentation.ui.home.navigaion.navigateHome
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.login.navigation.loginNavGraph
import com.devndev.lamp.presentation.ui.mypage.navigation.myPageNavGraph
import com.devndev.lamp.presentation.ui.mypage.navigation.navigateMyPage
import com.devndev.lamp.presentation.ui.search.navigation.searchNavGraph
import com.devndev.lamp.presentation.ui.signup.navigation.signupNavGraph
import com.devndev.lamp.presentation.ui.splsh.navigaion.splashNavGraph
import com.devndev.lamp.presentation.ui.theme.BackGroundColor
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray

@Composable
fun MainScreen(modifier: Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(Unit) {
        loginViewModel.checkLoginStatus()
    }

    val isLoggedIn by AuthManager.isLoggedIn.collectAsState()
    val isLoading by AuthManager.isLoading.collectAsState()
    Scaffold(
        containerColor = BackGroundColor,
        topBar = {
            if (currentRoute != Route.LOGIN && currentRoute != Route.SIGNUP) {
                LampTopBar()
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }
        },
        bottomBar = {
            if (currentRoute != Route.SEARCH &&
                currentRoute != Route.CREATION &&
                currentRoute != Route.LOGIN &&
                currentRoute != Route.SIGNUP
            ) {
                LampBottomNavigation(navController = navController)
            } else {
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = if (isLoading) Route.SPLASH else if (isLoggedIn) Route.HOME else Route.LOGIN
        ) {
            homeNavGraph(padding = innerPadding, navController = navController)
            chattingNavGraph(padding = innerPadding)
            myPageNavGraph(padding = innerPadding)
            searchNavGraph(padding = innerPadding, navController = navController)
            creationNavGraph(padding = innerPadding, navController = navController)
            splashNavGraph(padding = PaddingValues())
            loginNavGraph(padding = PaddingValues(), navController = navController)
            signupNavGraph(padding = PaddingValues(), navController = navController)
        }
    }
}

@Composable
fun LampTopBar() {
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
            tint = LightGray,
            modifier = Modifier.height(24.dp)
        )
        Icon(
            painterResource(id = R.drawable.alarm_icon),
            contentDescription = "AlarmIcon",
            tint = LightGray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun LampBottomNavigation(navController: NavController) {
    BottomNavigation(
        backgroundColor = LampBlack,
        contentColor = Color.White,
        modifier = Modifier
            .height(70.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val homeNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.HOME) { inclusive = true }
        }

        val chattingNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.CHATTING) { inclusive = true }
        }

        val myPageNavOptions = navOptions {
            launchSingleTop = true
            popUpTo(Route.MYPAGE) { inclusive = true }
        }

        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = Route.HOME,
                        tint = if (currentRoute == Route.HOME) Color.White else LightGray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == Route.HOME,
            onClick = {
                navController.navigateHome(homeNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.chatting),
                        contentDescription = Route.CHATTING,
                        tint = if (currentRoute == Route.CHATTING) Color.White else LightGray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == Route.CHATTING,
            onClick = {
                navController.navigateChatting(chattingNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.mypage),
                        contentDescription = Route.MYPAGE,
                        tint = if (currentRoute == Route.MYPAGE) Color.White else LightGray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == Route.MYPAGE,
            onClick = {
                navController.navigateMyPage(myPageNavOptions)
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
    }
}
