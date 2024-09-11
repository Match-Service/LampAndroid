package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun SearchList(profileList: List<Item>, onEnterButtonClick: (Item) -> Unit) {
    LazyColumn {
        items(profileList) { profile ->
            SearchItem(profile = profile, onEnterButtonClick = onEnterButtonClick)
        }
    }
}

@Composable
fun SearchItem(profile: Item, onEnterButtonClick: (Item) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.testimage),
                    contentDescription = "testimage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                NameSpace(profile = profile)
            }

            val buttonColors: Color
            val textColor: Color
            var buttonText = ""
            var onButtonClick = {}
            if (profile.isLampOn) {
                buttonColors = WomanColor
                textColor = Color.White
                buttonText = context.getString(R.string.enter_lamp)
                onButtonClick = { onEnterButtonClick(profile) }
            } else {
                buttonColors = Gray
                textColor = Gray3
                buttonText = context.getString(R.string.no_lamp)
            }
            // 기본적으로 button 에 default padding 이 적용되어 있어서 버튼 크기 상이하여 커스텀 버튼 생성, 추후 방법 찾으면 수정 필요
//            Button(
//                onClick = {},
//                shape = RoundedCornerShape(30.dp),
//                colors = buttonColors,
//                contentPadding = PaddingValues(horizontal = 15.dp)
//            ) {
//                Text(text = buttonText, style = Typography.normal12)
//            }
            SearchScreenButton(
                text = buttonText,
                onClick = onButtonClick,
                backgroundColor = buttonColors,
                textColor = textColor
            )
        }
    }
}

@Composable
fun NameSpace(profile: Item) {
    if (profile.isLampOn) {
        Column() {
            Text(
                text = profile.name,
                style = Typography.medium18,
                color = Color.White
            )
            Text(
                text = profile.roomName ?: "",
                style = Typography.normal12,
                color = Gray3
            )
        }
    } else {
        Text(
            text = profile.name,
            style = Typography.medium18,
            color = Color.White
        )
    }
}

@Composable
fun SearchScreenButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = Typography.normal12
        )
    }
}
