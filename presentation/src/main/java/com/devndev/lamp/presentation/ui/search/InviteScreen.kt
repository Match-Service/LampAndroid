package com.devndev.lamp.presentation.ui.search

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.CircleProfile
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SearchStatus
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.home.navigation.navigateHome

@Composable
fun InviteScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val logTag = "InviteScreen"
    BackHandler {
        navController.navigateHome()
    }

    val searchStatus by searchViewModel.searchStatus.collectAsState()

    val myInfo by searchViewModel.myInfo.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    val selectedItems = remember { mutableStateListOf<UserDomainModel>() }

    val users by remember { mutableStateOf(searchViewModel.users) }
    val showBottomButton = selectedItems.isNotEmpty()

    val recentUsers by remember { mutableStateOf(searchViewModel.recentUsers) }

    LaunchedEffect(Unit) {
        searchViewModel.resetUsers()
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f)
        ) {
            TopNavigationBar(
                text = stringResource(id = R.string.invite_friend),
                isNeedXButton = false,
                onBackButtonClick = { navController.navigateHome() }
            )

            var rowVisible by remember { mutableStateOf(false) }

            LaunchedEffect(selectedItems.size) {
                rowVisible = selectedItems.isNotEmpty()
            }

            val alpha by animateFloatAsState(
                targetValue = if (rowVisible) 1f else 0f,
                animationSpec = tween(durationMillis = 600),
                label = ""
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .animateContentSize(animationSpec = tween(durationMillis = 400))
                    .alpha(alpha),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                selectedItems.forEach { item ->
                    CircleProfile(
                        profile = item,
                        onDeleteButtonClick = {
                            selectedItems.remove(item)
                        },
                        isCanDelete = true
                    )
                }
            }

            LampTextField(
                width = 0,
                isNeedClearFocus = searchStatus == SearchStatus.SEARCHED,
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    if (searchQuery.isEmpty()) {
                        searchViewModel.updateSearchStatus(SearchStatus.NONE)
                    } else {
                        searchViewModel.updateSearchStatus(SearchStatus.SEARCHING)
                    }
                },
                hintText = stringResource(id = R.string.guide_search_friend),
                isSearchMode = true,
                onSearchKeyEvent = {
                    searchViewModel.searchInviteUsers(searchQuery)
                }
            )

            if (searchStatus == SearchStatus.USER_NOT_FOUNT) {
                Text(
                    text = stringResource(id = R.string.user_not_found),
                    style = Typography.normal12,
                    color = WomanColor,
                    textAlign = TextAlign.Center
                )
            }

            InviteList(
                searchUserList = users,
                recentUserList = recentUsers,
                selectedItems = selectedItems,
                onCheckedItemChanged = { checkedItem, isSelected ->
                    if (isSelected) {
                        selectedItems.add(checkedItem)
                    } else {
                        selectedItems.remove(checkedItem)
                    }
                },
                myName = myInfo?.name ?: ""
            )
        }

        if (showBottomButton) {
            BottomSpaceForInvite(onClick = {
                Log.d(logTag, selectedItems.toString())
                val selectedCount = selectedItems.size

                if (selectedCount == 0) return@BottomSpaceForInvite

                val message = if (selectedCount == 1) {
                    context.getString(R.string.invite_toast_msg, selectedItems[0].name)
                } else {
                    context.getString(
                        R.string.invite_many_toast_mag,
                        selectedItems[0].name,
                        selectedCount - 1
                    )
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                val selectedIds = selectedItems.map { it.id }
                searchViewModel.inviteUsers(selectedIds)
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun BottomSpaceForInvite(onClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LampButton(
            isGradient = true,
            buttonText = stringResource(id = R.string.invite),
            onClick = onClick,
            enabled = true
        )
    }
}
