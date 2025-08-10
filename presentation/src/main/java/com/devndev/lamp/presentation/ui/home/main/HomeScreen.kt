package com.devndev.lamp.presentation.ui.home.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.getMainColor
import com.devndev.lamp.presentation.ui.home.findlamp.FindLampScreen
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingFailHomeScreen
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingHomeScreen
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingSuccessHomeScreen
import com.devndev.lamp.presentation.ui.home.normal.NormalHomeScreen
import com.devndev.lamp.presentation.ui.home.vote.MatchingVoteScreen
import com.devndev.lamp.presentation.ui.home.waiting.WaitingHomeScreen
import kotlin.system.exitProcess

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController,
    isAssessmentExist: (Boolean) -> Unit,
    onTopBarVisibleChange: (Boolean) -> Unit,
    onBottomBarVisibleChange: (Boolean) -> Unit
) {
    val logTag = "HomeScreen"
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce = remember { false }
    val matchSuggestion by viewModel.matchSuggestion.collectAsState()
    val isFind by viewModel.isFind.collectAsState()
    val isMatchingSuccess by viewModel.isSuccess.collectAsState()

    val updateEvent = viewModel.updateEvent
    val updateStatus = viewModel.updateStatus

    val userStatus by viewModel.userStatue.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(matchSuggestion?.lampId) {
//        matchSuggestion?.lampId?.let { lampId ->
//            viewModel.loadFindState(lampId, false)
//        }
//    }

    DisposableEffect(lifecycleOwner) {
        viewModel.addStatusListener()
        onDispose {
            viewModel.removeStatusListener()
        }
    }

    BackHandler {
        Log.d(logTag, "back button clicked")
        if (backPressedOnce) {
            Log.d(logTag, "back button clicked 2 times end process")
            exitProcess(0)
        } else {
            Log.d(logTag, "back button clicked 1 times show Toast")
            Toast.makeText(
                context,
                context.getString(R.string.back_button_end_message),
                Toast.LENGTH_SHORT
            ).show()
            backPressedOnce = true
            handler.postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (userStatus) {
            "ON_BOARDING" -> {
                // matchingSuccess 화면 띄우는 경우
                Log.e("isMatchingSuccess", isMatchingSuccess.toString())
                if (isMatchingSuccess) {
                    MatchingSuccessHomeScreen(
                        modifier = modifier,
                        navController = navController,
                        updateEvent = updateEvent,
                        updateStatus = updateStatus,
                        onBottomScreen = onBottomBarVisibleChange
                    )
                } else {
                    NormalHomeScreen(
                        modifier = modifier,
                        navController = navController,
                        isAssessmentExist = {
                            isAssessmentExist(it)
                        }
                    )
                }
            }

            "PREPARE",
            "MATCHING" -> {
                MatchingHomeScreen(
                    modifier = modifier,
                    navController = navController,
                    updateEvent = updateEvent,
                    updateStatus = updateStatus
                )
            }

            "FIND_LAMP" -> {
                Log.e("isFind", isFind.toString())
                if (isFind != null) {
                    if (isFind!!) {
                        MatchingVoteScreen(
                            modifier = Modifier,
                            navController = navController,
                            onTopScreen = onTopBarVisibleChange,
                            onBottomScreen = onBottomBarVisibleChange
                        )
                    } else {
                        FindLampScreen(modifier = Modifier, navController = navController)
                    }
                }
            }

            "VISIT_WAITING" -> {
                WaitingHomeScreen(modifier = modifier, navController = navController)
            }

            "FAILED" -> {
                MatchingFailHomeScreen(
                    modifier = modifier,
                    navController = navController,
                    updateEvent = updateEvent,
                    updateStatus = updateStatus,
                    onBottomScreen = onBottomBarVisibleChange
                )
            }

//            "FINISHED" -> {
//                MatchingSuccessHomeScreen(
//                    modifier = modifier,
//                    navController = navController,
//                    updateEvent = updateEvent,
//                    updateStatus = updateStatus,
//                    onBottomScreen = onBottomBarVisibleChange
//                )
//            }
//
//        "IN_PROGRESS" -> TODO("Not yet implementation")
//
//
        }
    }
}

@Composable
fun HomeTextArea(
    nameText: String,
    middleText: String,
    bottomText: String,
    gender: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nameText,
            color = getMainColor(gender),
            style = IncTypography.normal42
        )
        Text(
            text = middleText,
            color = Color.White,
            style = Typography.semiBold32
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = bottomText,
            color = Color.White,
            style = Typography.normal12
        )
    }
}
