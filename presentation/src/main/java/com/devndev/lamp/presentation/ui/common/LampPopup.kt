package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun OneButtonPopup(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .width(340.dp)
                .background(Gray, shape = RoundedCornerShape(15.dp))
                .padding(top = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // todo 추후 팝업 내용 수정 서버에서 어떻게 받느냐에 따라 달라짐
                Column() {
                    Text(text = "목적", color = Color.White, style = Typography.medium15)
                    Text(text = "행사 및 마케팅 활동", color = Gray3, style = Typography.normal12)
                }
                Column() {
                    Text(text = "항목", color = Color.White, style = Typography.medium15)
                    Text(text = "이메일", color = Gray3, style = Typography.normal12)
                }
                Column() {
                    Text(text = "보유 및 이용기간", color = Color.White, style = Typography.medium15)
                    Text(
                        text = "정보제공 동의일로부터 회원탈퇴 및 동의 철회 시",
                        color = Gray3,
                        style = Typography.normal12
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                HorizontalDivider(thickness = 1.dp, color = Gray3)
                Text(
                    text = stringResource(id = R.string.close),
                    color = Color.White,
                    style = Typography.medium18,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onDismissRequest() }
                )
            }
        }
    }
}

@Composable
fun TwoButtonPopup(onStartButtonClick: () -> Unit, onEndButtonClick: () -> Unit) {
    Dialog(
        onDismissRequest = { }
    ) {
        Column(
            modifier = Modifier
                .width(340.dp)
                .background(Gray, shape = RoundedCornerShape(15.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.delete_profile_image),
                    color = Color.White,
                    style = Typography.semiBold20
                )
            }
            Column(modifier = Modifier.height(44.dp)) {
                HorizontalDivider(thickness = 1.dp, color = Gray3)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no),
                        color = Color.White,
                        style = Typography.medium18,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onStartButtonClick() }
                    )
                    VerticalDivider(thickness = 1.dp, color = Gray3)
                    Text(
                        text = stringResource(id = R.string.yes),
                        color = Color.White,
                        style = Typography.medium18,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onEndButtonClick() }
                    )
                }
            }
        }
    }
}
