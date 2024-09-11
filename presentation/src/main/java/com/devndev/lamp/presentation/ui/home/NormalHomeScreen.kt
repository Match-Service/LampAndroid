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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButtonWithIcon
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.search.navigation.navigateSearch

@Composable
fun NormalHomeScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current

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
                nameText = "북창동루쥬라 " + context.getString(R.string.sir),
                middleText = context.getString(R.string.main_header),
                bottomText = context.getString(R.string.meet_with_lamp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            LampButtonWithIcon(
                isGradient = true,
                buttonText = context.getString(R.string.make_lamp),
                guideButtonText = context.getString(R.string.guide_make_lamp),
                onClick = {
                    navController.navigateCreation()
                }
            )
            LampButtonWithIcon(
                isGradient = false,
                buttonText = context.getString(R.string.find_friend),
                guideButtonText = context.getString(R.string.guide_find_friend),
                onClick = {
                    navController.navigateSearch()
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
