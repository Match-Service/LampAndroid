package com.devndev.lamp.presentation.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.chatting.navigation.chattingNavGraph
import com.devndev.lamp.presentation.ui.chatting.navigation.navigateChatting
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.navigaion.homeNavGraph
import com.devndev.lamp.presentation.ui.home.navigaion.navigateHome
import com.devndev.lamp.presentation.ui.mypage.navigation.myPageNavGraph
import com.devndev.lamp.presentation.ui.mypage.navigation.navigateMyPage
import com.devndev.lamp.presentation.ui.theme.BackGroundColor
import com.devndev.lamp.presentation.ui.theme.LightGray

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BackHandler {
        Log.d("llll", "back button clicked")
    }

    Scaffold(
        containerColor = BackGroundColor,
        topBar = { LampTopBar() },
        bottomBar = {
            LampBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Route.HOME) {
            homeNavGraph(padding = innerPadding)
            chattingNavGraph(padding = innerPadding)
            myPageNavGraph(padding = innerPadding)
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
        backgroundColor = Color.Black,
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
