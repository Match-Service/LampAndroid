package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.home.TempStatus
import com.devndev.lamp.presentation.ui.main.navigation.navigateMain

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }

    val users by remember { mutableStateOf(viewModel.users) }

    val showBottomSpace = users.any { it.lampId == null }

    LaunchedEffect(Unit) {
        viewModel.resetUsers()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f)
        ) {
            TopNavigationBar(
                text = stringResource(id = R.string.find_friend),
                isNeedXButton = false,
                onBackButtonClick = { navController.navigateMain(MainScreenPage.HOME) }
            )

            LampTextField(
                width = 0,
                isGradient = false,
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                hintText = stringResource(id = R.string.guide_search_friend),
                isSearchMode = true,
                onSearchKeyEvent = {
                    viewModel.searchUsers(searchQuery)
                }
            )

            SearchList(
                profileList = users,
                onEnterButtonClick = { profile ->
                    TempStatus.updateIsWaiting(true)
                    TempStatus.updateProfileName(profile.name)
                    navController.navigateMain(MainScreenPage.HOME)
                }
            )
        }
        if (showBottomSpace) {
            BottomSpace(navController = navController)
        }
    }
}

@Composable
fun BottomSpace(navController: NavController) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.search_make_lame_guide),
            color = Color.White,
            style = Typography.normal12
        )
        LampButton(
            isGradient = true,
            buttonText = stringResource(id = R.string.make_lamp),
            onClick = { navController.navigateCreation() },
            enabled = true
        )
    }
}
