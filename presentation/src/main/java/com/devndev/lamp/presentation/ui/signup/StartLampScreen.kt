package com.devndev.lamp.presentation.ui.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.login.navigation.navigateLogin
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun StartLampScreen(navController: NavController) {
    BackHandler {
        val navOption = navOptions {
            launchSingleTop = true
            popUpTo(Route.START_LAMP) { inclusive = true }
        }
        navController.navigateLogin(navOption)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.5f))
            Text(
                text = stringResource(id = R.string.sign_up_complete),
                color = Color.White,
                style = Typography.semiBold25,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(3f))
            LampButton(
                isGradient = true,
                buttonText = stringResource(id = R.string.start),
                onClick = { /*TODO*/ },
                enabled = true,
                icon = painterResource(id = R.drawable.app_logo)
            )
        }
    }
}
