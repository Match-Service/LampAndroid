package com.devndev.lamp.presentation.ui.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.IncTypography
import com.devndev.lamp.presentation.ui.theme.MainColor
import com.devndev.lamp.presentation.ui.theme.Typography
import kotlin.system.exitProcess

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val logTag = "HomeScreen"
    val context = LocalContext.current
    val items: List<Item> by viewModel.items.collectAsState()
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce = remember { false }

    val isWaiting by TempStatus.isWaiting.collectAsState()
    val profileName by TempStatus.profileName.collectAsState()
    val isMatching by TempStatus.isMatching.collectAsState()

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
    if (isWaiting) {
        WaitingHomeScreen(modifier = modifier, navController = navController)
    } else if (isMatching) {
//        MatchingHomeScreen(modifier = modifier, navController = navController)
        MatchingVoteScreen(modifier = modifier, navController = navController)
    } else {
        NormalHomeScreen(modifier = modifier, navController = navController)
    }
}

@Composable
fun HomeTextArea(
    nameText: String,
    middleText: String,
    bottomText: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nameText,
            color = MainColor,
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
