package com.devndev.lamp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.MainColor

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), modifier: Modifier) {
    val items: List<Item> by viewModel.items.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(70.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //todo font 적용
            Text(
                text = "북창동루쥬라 님",
                color = MainColor,
                fontSize = 42.sp,
                fontWeight = FontWeight(400),
                lineHeight = 56.sp
            )
            Text(
                text = "램프를 비춰볼까요?",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight(600),
                lineHeight = 42.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "램프와 함께 빛나는 만남을 찾아보세요",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                lineHeight = 16.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LampButton(isGradient = true, buttonText = "램프 생성하기", "램프를 만들고 만남을 시작해 보세요")
            LampButton(isGradient = false, buttonText = "친구 찾아가기", "친구를 검색하고 램프를 찾아가보세요")

        }

    }
}
