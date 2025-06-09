package com.devndev.lamp.presentation.ui.appointment.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.appointment.AppointmentViewModel
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.LampDateTimePicker
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup

@Composable
fun RegisterAppointmentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    isEdit: Boolean,
    chatRoomId: Int,
    chatAppointmentId: Int,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val state by appointmentViewModel.uiState.collectAsStateWithLifecycle()

    var isDatePickerShow by remember { mutableStateOf(false) }
    var isConfirmPopupShow by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (isEdit) {
            appointmentViewModel.getAppointmentInfo(
                chatRoomId,
                chatAppointmentId
            )
        }
    }

    val focusManager = LocalFocusManager.current

    if (isConfirmPopupShow) {
        val confirmString = if (isEdit) {
            stringResource(R.string.appointment_edit_confirm)
        } else {
            stringResource(R.string.appointment_confirm)
        }
        TwoButtonPopup(
            mainText = "${state.location}\n${state.date}\n $confirmString",
            startButtonText = stringResource(id = R.string.no),
            endButtonText = stringResource(id = R.string.yes),
            onStartButtonClick = { isConfirmPopupShow = false },
            onEndButtonClick = {
                isConfirmPopupShow = false
                if (!isEdit) {
                    appointmentViewModel.registerAppointment(chatRoomId)
                } else {
                    appointmentViewModel.editAppointment()
                }
            }
        )
    }

    if (isDatePickerShow) {
        LampDateTimePicker(
            onDateTimeSelected = {
                appointmentViewModel.updateDate(it)
            },
            onDismiss = {
                isDatePickerShow = false
            }
        )
    }

    LaunchedEffect(state.needNavBack) {
        if (state.needNavBack) {
            navController.popBackStack()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        val topBarString = if (isEdit) {
            stringResource(R.string.edit_appointment)
        } else {
            stringResource(R.string.register_appointment_button)
        }
        TopNavigationBar(
            text = topBarString,
            isNeedXButton = false,
            onBackButtonClick = { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(25.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.location),
                    style = Typography.medium15,
                    color = Color.White
                )
            }
            LampTextField(
                width = 300,
                query = state.location,
                onQueryChange = {
                    appointmentViewModel.updateLocation(it)
                },
                hintText = stringResource(R.string.hint_appointment_location)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.date),
                    style = Typography.medium15,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(27.dp))
                    .border(
                        width = 1.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(27.dp)
                    )
                    .clickable {
                        isDatePickerShow = true
                    }
                    .padding(horizontal = 20.dp, vertical = 7.dp)
            ) {
                if (state.date.isNotEmpty()) {
                    Text(
                        text = state.date,
                        color = Color.White,
                        style = Typography.medium18
                    )
                } else {
                    Text(
                        text = stringResource(R.string.hint_appointment_date),
                        color = LightGray,
                        style = Typography.medium18
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 10.dp)
        ) {
            LampButton(
                isGradient = true,
                buttonText = topBarString,
                onClick = {
                    isConfirmPopupShow = true
                },
                enabled = state.location.isNotEmpty() && state.date.isNotEmpty()
            )
        }
    }
}
