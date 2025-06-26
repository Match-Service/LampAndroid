package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.devndev.lamp.domain.model.chat.UserInfo
import com.devndev.lamp.domain.model.lampmatch.IndividualityDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.home.vote.ProgressBar
import com.devndev.lamp.presentation.ui.mypage.calculateManAge
import com.devndev.lamp.presentation.utils.InstagramUtils

@Composable
fun OneButtonPopup(
    text: String?,
    onDismissRequest: () -> Unit
) {
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
            if (text == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
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
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = text,
                        color = Color.White,
                        style = Typography.semiBold20,
                        textAlign = TextAlign.Center
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
fun TwoButtonPopup(
    mainText: String,
    startButtonText: String,
    endButtonText: String,
    hintText: String = "",
    onStartButtonClick: () -> Unit,
    onEndButtonClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { }
    ) {
        Column(
            modifier = Modifier
                .width(340.dp)
                .background(Gray, shape = RoundedCornerShape(15.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = mainText,
                    color = Color.White,
                    style = Typography.semiBold20,
                    textAlign = TextAlign.Center
                )
                if (hintText.isNotEmpty()) {
                    Text(
                        text = hintText,
                        color = Color.White,
                        style = Typography.medium15,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(modifier = Modifier.height(44.dp)) {
                HorizontalDivider(thickness = 1.dp, color = LightGray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = startButtonText,
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
                    VerticalDivider(thickness = 1.dp, color = LightGray)
                    Text(
                        text = endButtonText,
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

@Composable
fun EditPopup(
    text: String,
    queryString: String,
    onXButtonClick: () -> Unit,
    onEditButtonClick: (String) -> Unit,
    instagramAuthStep: Int = InstagramAuth.NONE,
    onInstagramQueryChange: (Int) -> Unit = {},
    isNeedClearFocus: Boolean = false
) {
    var query by remember { mutableStateOf(queryString) }
    Dialog(onDismissRequest = {}, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                onXButtonClick()
                            },
                        painter = painterResource(id = R.drawable.x_button_big),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray, shape = RoundedCornerShape(15.dp))
                        .padding(vertical = 40.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(17.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    LampTextField(
                        width = 270,
                        isNeedClearFocus = isNeedClearFocus,
                        query = query,
                        onQueryChange = {
                            query = it
                            if (instagramAuthStep == InstagramAuth.AUTH_SUCCESS) {
                                onInstagramQueryChange(InstagramAuth.BEFORE_AUTH)
                            }
                        },
                        hintText = ""
                    )
                    if (instagramAuthStep == InstagramAuth.AUTH_SUCCESS) {
                        Text(
                            text = stringResource(id = R.string.auth_success),
                            color = Color.White,
                            style = Typography.normal12
                        )
                    } else if (instagramAuthStep == InstagramAuth.AUTH_FAIL) {
                        Text(
                            text = stringResource(id = R.string.auth_failure),
                            color = WomanColor,
                            style = Typography.normal12
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            val buttonText =
                if (instagramAuthStep == InstagramAuth.BEFORE_AUTH || instagramAuthStep == InstagramAuth.AUTH_FAIL) {
                    stringResource(id = R.string.authentication)
                } else {
                    stringResource(id = R.string.edit)
                }
            LampButton(
                isGradient = true,
                buttonText = buttonText,
                onClick = { onEditButtonClick(query) },
                enabled = true
            )
        }
    }
}

@Composable
fun ProfilePopup(
    userInfo: UserInfo? = null,
    onXButtonClick: () -> Unit
) {
    val context = LocalContext.current

    val nameColor = if (userInfo?.gender == "MALE") {
        ManColor
    } else {
        WomanColor
    }

    val attractive = userInfo?.individuality?.let {
        listOf(it.personality, it.voice, it.fashion, it.conversation)
    } ?: listOf(0, 0, 0, 0)

    val avgAttractive = attractive.average().toInt()

    val attractiveDomainModel = listOf(
        IndividualityDomainModel(
            avgAttractive,
            attractive[0],
            attractive[1],
            attractive[2],
            attractive[3]
        )
    )

    Dialog(onDismissRequest = {}, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 15.dp)
                            .clickable {
                                onXButtonClick()
                            },
                        painter = painterResource(id = R.drawable.x_button_big),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray, shape = RoundedCornerShape(15.dp))
                        .padding(vertical = 25.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        userInfo?.profileImages?.forEach { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.height(56.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userInfo?.name ?: "",
                            color = nameColor,
                            style = IncTypography.normal42
                        )
                        Text(
                            text = stringResource(id = R.string.sir),
                            color = nameColor,
                            style = IncTypography.normal42
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.instagram_icon),
                            tint = Color.White,
                            contentDescription = "instagram",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    InstagramUtils.openInstagramProfile(context, userInfo?.instagramId ?: "")
                                }
                        )
                    }
                    Row() {
                        Text(
                            text = calculateManAge(
                                userInfo?.birth ?: ""
                            ) + stringResource(id = R.string.age),

                            fontSize = 18.sp,
                            color = Color.White
                        )
                        if (!userInfo?.jobName.isNullOrEmpty()) {
                            Text(
                                text = (", " + userInfo?.jobName),
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.heart),
                                contentDescription = "Heart",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${stringResource(id = R.string.attractiveness)} $avgAttractive",
                                color = Color.White,
                                style = Typography.medium18.copy(lineHeight = 20.sp),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(9.dp))

                        ProgressBar(attractiveDomainModel, 0)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = userInfo?.bio ?: "",
                        textAlign = TextAlign.Center,
                        style = Typography.normal12,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        userInfo?.bioQuestions?.forEachIndexed { index, bioQuestion ->
                            Text(
                                text = "${bioQuestion.question} : ${bioQuestion.answer}",
                                style = Typography.medium10,
                                color = Gray3
                            )
                            if (index != userInfo.bioQuestions.lastIndex) {
                                Text(
                                    text = " | ",
                                    style = Typography.medium10,
                                    color = Gray3
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
