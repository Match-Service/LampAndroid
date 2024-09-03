package com.devndev.lamp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.IncTypography
import com.devndev.lamp.presentation.ui.theme.MainColor
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), modifier: Modifier) {
    val items: List<Item> by viewModel.items.collectAsState()

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "북창동루쥬라 " + context.getString(R.string.sir),
                color = MainColor,
                style = IncTypography.normal42
            )
            Text(
                text = context.getString(R.string.main_header),
                color = Color.White,
                style = Typography.semiBold32
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = context.getString(R.string.meet_with_lamp),
                color = Color.White,
                style = Typography.normal12
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            LampButton(
                isGradient = true,
                buttonText = context.getString(R.string.make_lamp),
                guideButtonText = context.getString(R.string.guide_make_lamp),
                onClick = {}
            )
            LampButton(
                isGradient = false,
                buttonText = context.getString(R.string.find_friend),
                guideButtonText = context.getString(R.string.guide_find_friend),
                onClick = {}
            )
        }
        Spacer(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        )
    }
}
