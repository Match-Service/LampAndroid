package com.devndev.lamp.presentation.ui.mypage

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.home.ProgressBar
import com.devndev.lamp.presentation.ui.mypage.navigation.navigateProfileEdit
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.IncTypography
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

@Composable
fun MyPageScreen(
    modifier: Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    navController: NavController
) {
    val logTag = "MyPageScreen"
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce by remember { mutableStateOf(false) }
    val attractive = listOf(70, 80, 50, 40)
    val avgAttractive = attractive.average()

    val myInfo by viewModel.myInfo.collectAsState()

    val birthdate = myInfo?.birth?.takeIf { it.isNotBlank() } ?: "000000"

    val alarmsState = remember {
        AlarmsState()
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

    val outlineModifier = Modifier
        .width(300.dp)
        .border(
            width = 1.dp,
            color = LightGray,
            shape = RoundedCornerShape(15.dp)
        )
        .padding(horizontal = 20.dp, vertical = 10.dp)

    LazyColumn(
        modifier = modifier
            .background(LampBlack)
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            UserInfoSection(
                url = myInfo?.profileImages?.get(0)?.url,
                navController = navController,
                name = myInfo?.name ?: "",
                age = calculateManAge(birthdate = birthdate),
                university = "한국대학교"
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                AttractiveSection(
                    modifier = outlineModifier,
                    avgAttractive = avgAttractive.toInt(),
                    attractive = attractive
                )
                AlarmSettingsSection(modifier = outlineModifier, alarmsState = alarmsState)
                AskQuestionSection(modifier = outlineModifier)
                LogOutSection(modifier = outlineModifier, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun UserInfoSection(
    url: String?,
    navController: NavController,
    name: String,
    age: String,
    university: String?
) {
    val navOption = navOptions {
        launchSingleTop = true
    }
    Row(
        modifier = Modifier
            .width(300.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                navController.navigateProfileEdit(navOption)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.height(60.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "$name 님",
                    color = Color.White,
                    style = IncTypography.normal30
                )
                val infoText = if (university == null) {
                    "${age}세"
                } else {
                    "${age}세, 한국대학교"
                }
                Text(
                    text = infoText,
                    color = Gray3,
                    style = Typography.normal14
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )
    }
}

@Composable
fun AttractiveSection(modifier: Modifier, avgAttractive: Int, attractive: List<Int>) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                tint = WomanColor
            )
            Text(
                text = "${stringResource(id = R.string.attractiveness)} $avgAttractive",
                color = WomanColor,
                style = Typography.medium15
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (avgAttractive == 0) {
            Text(
                text = stringResource(id = R.string.no_attractive),
                color = LightGray,
                style = Typography.normal12
            )
        } else {
            ProgressBar(attractive = attractive, barColor = Color.White)
        }
    }
}

@Composable
fun AlarmSettingsSection(modifier: Modifier, alarmsState: AlarmsState) {
    val context = LocalContext.current
    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SwitchWithText(
            text = stringResource(id = R.string.push_alarm),
            isChecked = alarmsState.isPushAlarmChecked,
            onCheckedChange = { isChecked ->
                alarmsState.isPushAlarmChecked = isChecked
                if (isChecked) {
                    if (alarmsState.areAllAlarmsUnchecked()) {
                        alarmsState.checkAllAlarms()
                    }
                    showAgreeToast(context, true)
                } else {
                    showAgreeToast(context, false)
                }
            }
        )

        if (alarmsState.isPushAlarmChecked) {
            AlarmSwitches(alarmsState)
        }
    }
}

@Composable
fun AlarmSwitches(alarmsState: AlarmsState) {
    SwitchWithText(
        text = stringResource(id = R.string.invite_lamp_alarm),
        hintText = stringResource(id = R.string.guide_invite_lamp_alarm),
        isChecked = alarmsState.isInviteAlarmChecked,
        onCheckedChange = {
            alarmsState.isInviteAlarmChecked = it
            if (alarmsState.areAllAlarmsUnchecked()) {
                alarmsState.isPushAlarmChecked = false
            }
        }
    )
    SwitchWithText(
        text = stringResource(id = R.string.visit_lamp_alarm),
        hintText = stringResource(id = R.string.guide_visit_lamp_alarm),
        isChecked = alarmsState.isVisitAlarmChecked,
        onCheckedChange = {
            alarmsState.isVisitAlarmChecked = it
            if (alarmsState.areAllAlarmsUnchecked()) {
                alarmsState.isPushAlarmChecked = false
            }
        }
    )
    SwitchWithText(
        text = stringResource(id = R.string.match_alarm),
        hintText = stringResource(id = R.string.guide_match_alarm),
        isChecked = alarmsState.isMatchAlarmChecked,
        onCheckedChange = {
            alarmsState.isMatchAlarmChecked = it
            if (alarmsState.areAllAlarmsUnchecked()) {
                alarmsState.isPushAlarmChecked = false
            }
        }
    )
    SwitchWithText(
        text = stringResource(id = R.string.badge_alarm),
        hintText = stringResource(id = R.string.guide_badge_alarm),
        isChecked = alarmsState.isBadgeAlarmChecked,
        onCheckedChange = {
            alarmsState.isBadgeAlarmChecked = it
            if (alarmsState.areAllAlarmsUnchecked()) {
                alarmsState.isPushAlarmChecked = false
            }
        }
    )
    SwitchWithText(
        text = stringResource(id = R.string.message_alarm),
        hintText = stringResource(id = R.string.guide_message_alarm),
        isChecked = alarmsState.isMessageAlarmChecked,
        onCheckedChange = {
            alarmsState.isMessageAlarmChecked = it
            if (alarmsState.areAllAlarmsUnchecked()) {
                alarmsState.isPushAlarmChecked = false
            }
        }
    )
}

@Composable
fun SwitchWithText(
    text: String,
    hintText: String = "",
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onCheckedChange(!isChecked)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, color = Color.White, style = Typography.medium15)
            GradientSwitch(isChecked = isChecked, onCheckedChange = onCheckedChange)
        }
        if (hintText.isNotEmpty()) {
            Text(text = hintText, color = Gray3, style = Typography.medium10)
        }
    }
}

@Composable
fun GradientSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val switchWidth = 36.dp
    val toggleSize = 15.dp

    val toggleOffset = with(LocalDensity.current) {
        if (isChecked) (switchWidth.toPx() - toggleSize.toPx() - 6.dp.toPx()) else 0f
    }

    val togglePosition = remember { Animatable(0f) }

    LaunchedEffect(isChecked) {
        togglePosition.animateTo(toggleOffset)
    }

    val trackColor = if (isChecked) {
        Brush.linearGradient(
            colors = listOf(WomanColor, ManColor)
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(LightGray, LightGray)
        )
    }

    Box(
        modifier = modifier
            .size(switchWidth, 20.dp)
            .clickable { onCheckedChange(!isChecked) }
            .background(trackColor, RoundedCornerShape(50.dp))
            .padding(horizontal = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(toggleSize)
                .offset { IntOffset(togglePosition.value.toInt(), 0) }
                .align(Alignment.CenterStart)
                .background(if (isChecked) Color.White else Gray3, CircleShape)
        )
    }
}

@Composable
fun AskQuestionSection(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ask_question),
            color = Color.White,
            style = Typography.medium15
        )
        Text(
            text = stringResource(id = R.string.help_and_support),
            color = Gray3,
            style = Typography.normal12
        )
        Text(
            text = stringResource(id = R.string.delete_account),
            color = Gray3,
            style = Typography.normal12
        )
    }
}

@Composable
fun LogOutSection(modifier: Modifier, viewModel: MyPageViewModel) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                viewModel.signOut()
            },
            text = stringResource(id = R.string.logout),
            color = Color.White,
            style = Typography.medium15
        )
    }
}

fun calculateManAge(birthdate: String): String {
    // Check if the birthdate string is valid
    Log.d("MyPageScreen", "birthDate = $birthdate")
    if (birthdate == "000000") {
        return "0"
    }

    // Parse the birthdate string to a Date object
    val formatter = SimpleDateFormat("yyMMdd", Locale.getDefault()) // Format "970530"
    val birthDate =
        formatter.parse(birthdate) ?: throw IllegalArgumentException("Invalid birthdate format")

    // Get current date and year
    val birthCalendar = Calendar.getInstance().apply { time = birthDate }

    // Calculate age based on year difference
    var age = Calendar.getInstance().get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    // Check if birthday has occurred this year
    if (Calendar.getInstance().get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
        (
            Calendar.getInstance().get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                Calendar.getInstance()
                .get(Calendar.DAY_OF_MONTH) < birthCalendar.get(
                    Calendar.DAY_OF_MONTH
                )
            )
    ) {
        age -= 1
    }

    return age.toString()
}

fun showAgreeToast(context: Context, agree: Boolean) {
    // 현재 날짜 포맷팅
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    val formattedDate = dateFormat.format(currentDate)

    val message = if (agree) {
        context.getString(R.string.push_notification_agree_message, formattedDate)
    } else {
        context.getString(R.string.push_notification_disagree_message, formattedDate)
    }

    val centeredText = SpannableString(message)
    centeredText.setSpan(
        AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
        0,
        message.length - 2,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    Toast.makeText(context, centeredText, Toast.LENGTH_LONG).show()
}

class AlarmsState {
    var isPushAlarmChecked by mutableStateOf(false)
    var isInviteAlarmChecked by mutableStateOf(false)
    var isVisitAlarmChecked by mutableStateOf(false)
    var isMatchAlarmChecked by mutableStateOf(false)
    var isBadgeAlarmChecked by mutableStateOf(false)
    var isMessageAlarmChecked by mutableStateOf(false)

    fun checkAllAlarms() {
        isInviteAlarmChecked = true
        isVisitAlarmChecked = true
        isMatchAlarmChecked = true
        isBadgeAlarmChecked = true
        isMessageAlarmChecked = true
    }

    fun areAllAlarmsUnchecked(): Boolean {
        return !isInviteAlarmChecked && !isVisitAlarmChecked && !isMatchAlarmChecked && !isBadgeAlarmChecked && !isMessageAlarmChecked
    }
}
