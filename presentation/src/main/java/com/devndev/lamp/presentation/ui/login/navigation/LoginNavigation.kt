package com.devndev.lamp.presentation.ui.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    this.navigate(LoginRoute.ROUTE, navOptions)
}

// fun NavGraphBuilder.loginNavGraph(
//     padding: PaddingValues,
//     modifier: Modifier = Modifier
// ) {
//     composable(LoginRoute.ROUTE) {
//         LoginScreen(
//             modifier = modifier
//         )
//     }
// }

object LoginRoute {
    const val ROUTE = "login"
}
