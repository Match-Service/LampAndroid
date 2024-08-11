package com.devndev.lamp.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.ui.home.HomeScreen
import com.devndev.lamp.presentation.ui.s2.Screen2
import com.devndev.lamp.presentation.ui.s3.Screen3
import com.devndev.lamp.presentation.ui.s4.Screen4

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    onClick = {
                        navController.navigate("home")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "List") },
                    label = { Text("List") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == "list" } == true,
                    onClick = {
                        navController.navigate("list")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
                    label = { Text("Favorite") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == "favorite" } == true,
                    onClick = {
                        navController.navigate("favorite")
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = navController.currentDestination?.hierarchy?.any { it.route == "profile" } == true,
                    onClick = {
                        navController.navigate("profile")
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = "home") {
            composable("home") {
                HomeScreen(modifier = Modifier.padding(innerPadding))
            }
            composable("list") {
                Screen2()
            }
            composable("favorite") {
                Screen3()
            }
            composable("profile") {
                Screen4()
            }
        }
    }
}
