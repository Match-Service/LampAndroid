package com.devndev.lamp.presentation.main

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.home.HomeScreen
import com.devndev.lamp.presentation.ui.s2.Screen2
import com.devndev.lamp.presentation.ui.s4.Screen4
import com.devndev.lamp.presentation.ui.theme.BackGroundColor

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        containerColor = BackGroundColor,
        topBar = { LampTopBar() },
        bottomBar = {
            LampBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "home") {
            composable("home") {
                HomeScreen(modifier = Modifier.padding(innerPadding))
            }
            composable("chatting") {
                Screen2()
            }
            composable("mypage") {
                Screen4()
            }
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
            tint = Color.Gray,
            modifier = Modifier.height(24.dp)
        )
        Icon(
            painterResource(id = R.drawable.alarm_icon),
            contentDescription = "AlarmIcon",
            tint = Color.Gray,
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

        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        tint = if (currentRoute == "home") Color.White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    launchSingleTop = true
                    popUpTo("home") { saveState = true }
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.chating),
                        contentDescription = "Chatting",
                        tint = if (currentRoute == "chatting") Color.White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == "chatting",
            onClick = {
                navController.navigate("chatting") {
                    launchSingleTop = true
                    popUpTo("chatting") { saveState = true }
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
        BottomNavigationItem(
            icon = {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.mypage),
                        contentDescription = "MyPage",
                        tint = if (currentRoute == "mypage") Color.White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            },
            selected = currentRoute == "mypage",
            onClick = {
                navController.navigate("mypage") {
                    launchSingleTop = true
                    popUpTo("mypage") { saveState = true }
                }
            },
            modifier = Modifier.padding(top = 15.dp, bottom = 20.dp)
        )
    }
}
