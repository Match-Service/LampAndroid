package com.devndev.lamp.presentation.ui.home.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingHomeScreen
import com.devndev.lamp.presentation.ui.home.normal.NormalHomeScreen
import com.devndev.lamp.presentation.ui.home.vote.MatchingVoteScreen
import com.devndev.lamp.presentation.ui.home.waiting.WaitingHomeScreen
import kotlin.system.exitProcess

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val logTag = "HomeScreen"
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce = remember { false }

    val userStatus by viewModel.userStatus.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        viewModel.connectSocket()
        onDispose {
            viewModel.disconnectSocket()
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

    when (userStatus) {
        "ON_BOARDING" -> {
            NormalHomeScreen(modifier = modifier, navController = navController)
        }

        "PREPARE",
        "MATCHING" -> {
            MatchingHomeScreen(modifier = modifier, navController = navController)
        }

        "FIND_LAMP" -> {
            FindLampScreen(modifier = Modifier, navController = navController)
        }

        "VISIT_WAITING" -> {
            WaitingHomeScreen(modifier = modifier, navController = navController)
        }

        "VOTE" -> {
            MatchingVoteScreen(modifier = Modifier, navController = navController)
        }
//
//        "FAILED" -> TODO("Not yet implementation")
//
//        "IN_PROGRESS" -> TODO("Not yet implementation")
//
//
//        "FINISHED" -> TODO("Not yet implementation")
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
