package com.devndev.lamp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButtonWithIcon
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.search.navigation.navigateSearch

@Composable
fun NormalHomeScreen(modifier: Modifier, navController: NavController) {
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
                nameText = "북창동루쥬라 " + stringResource(id = R.string.sir),
                middleText = stringResource(id = R.string.main_header),
                bottomText = stringResource(id = R.string.meet_with_lamp)
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
                    navController.navigateCreation(navOption)
                },
                icon = painterResource(id = R.drawable.arrow),
                enabled = true,
                onIconClick = {
                    navController.navigateCreation(navOption)
                }
            )
            LampButtonWithIcon(
                isGradient = false,
                buttonText = stringResource(id = R.string.find_friend),
                guideButtonText = stringResource(id = R.string.guide_find_friend),
                onClick = {
                    navController.navigateSearch(navOption)
                },
                icon = painterResource(id = R.drawable.arrow),
                enabled = true,
                onIconClick = {
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
}
