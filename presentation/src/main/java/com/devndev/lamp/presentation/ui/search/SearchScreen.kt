package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.home.TempStatus
import com.devndev.lamp.presentation.ui.home.navigaion.navigateHome
import com.devndev.lamp.presentation.ui.theme.Typography

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
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.back_arrow),
                    contentDescription = "뒤로가기",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.popBackStack(Route.HOME, false)
                    }
                )
                Text(
                    text = stringResource(id = R.string.find_friend),
                    style = Typography.semiBold25,
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
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
                    navController.navigateHome()
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
