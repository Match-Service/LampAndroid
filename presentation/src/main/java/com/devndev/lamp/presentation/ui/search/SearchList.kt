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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup

@Composable
fun SearchList(profileList: List<UserDomainModel>, onEnterButtonClick: (UserDomainModel) -> Unit) {
    LazyColumn {
        items(profileList) { profile ->
            SearchItem(profile = profile, onEnterButtonClick = onEnterButtonClick)
        }
    }
}

@Composable
fun SearchItem(profile: UserDomainModel, onEnterButtonClick: (UserDomainModel) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        var isEnterPopupShow by remember { mutableStateOf(false) }

        if (isEnterPopupShow) {
            TwoButtonPopup(
                mainText = stringResource(id = R.string.enter_popup_main, profile.name),
                startButtonText = stringResource(id = R.string.no),
                endButtonText = stringResource(id = R.string.yes),
                onStartButtonClick = { isEnterPopupShow = false },
                onEndButtonClick = {
                    onEnterButtonClick(profile)
                    isEnterPopupShow = false
                }
            )
        }
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
                    painter = rememberAsyncImagePainter(profile.thumbnail),
                    contentDescription = "thumbnail",
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
            if (profile.lampId != null) {
                buttonColors = WomanColor
                textColor = Color.White
                buttonText = stringResource(id = R.string.enter_lamp)
                onButtonClick = { isEnterPopupShow = true }
            } else {
                buttonColors = Gray
                textColor = Gray3
                buttonText = stringResource(id = R.string.no_lamp)
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
fun NameSpace(profile: UserDomainModel) {
    val lampStatus = profile.lampStatus
    if (profile.lampId != null) {
        Text(
            text = profile.name,
            style = Typography.medium18,
            color = Color.White
        )
    } else {
        Column() {
            Text(
                text = profile.name,
                style = Typography.medium18,
                color = Color.White
            )
            var lampStatusText = ""
            when (lampStatus) {
                "NONE" ->
                    lampStatusText =
                        stringResource(id = R.string.find_lamp_status_none)

                "OTHER_LAMP" ->
                    lampStatusText =
                        stringResource(id = R.string.lamp_status_other_lamp)
                // todo lampStatus 인원 초과 추가 필요
            }
            Text(
                text = lampStatusText,
                style = Typography.normal12,
                color = WomanColor
            )
        }
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
