package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    val items: List<Item> by viewModel.items.collectAsState()
    val showBottomSpace = items.any { !it.isLampOn }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween // Ensure space between items
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f) // Fill available space
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
                    text = context.getString(R.string.find_friend),
                    style = Typography.semiBold25,
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LampBlack, shape = RoundedCornerShape(27.dp))
                    .border(
                        width = 1.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(27.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "검색 아이콘",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { newText -> searchQuery = newText },
                    textStyle = Typography.medium18.copy(color = Color.White),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .weight(1f)
                ) { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = context.getString(R.string.guide_search_friend),
                            color = LightGray,
                            style = Typography.medium18
                        )
                    }
                    innerTextField()
                }

                IconButton(
                    modifier = Modifier.size(10.dp),
                    onClick = {
                        searchQuery = ""
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_button),
                        contentDescription = "검색 지우기",
                        tint = LightGray
                    )
                }
            }
            SearchList(profileList = items)
        }
        if (showBottomSpace) {
            BottomSpace(navController = navController)
        }
    }
}

@Composable
fun BottomSpace(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.search_make_lame_guide),
            color = Color.White,
            style = Typography.normal12
        )
        LampButton(
            isGradient = true,
            buttonText = context.getString(R.string.make_lamp),
            onClick = { navController.navigateCreation() },
            enabled = true
        )
    }
}
