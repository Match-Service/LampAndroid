package com.devndev.lamp.presentation.ui.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.appointment.navigation.navigateRegisterAppointment
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TopNavigationBar

@Composable
fun AppointmentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    chatRoomId: Int,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val state by appointmentViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        appointmentViewModel.getChatInfo(chatRoomId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            TopNavigationBar(
                text = stringResource(R.string.make_appointment),
                onBackButtonClick = {
                    navController.popBackStack()
                },
                onXButtonClick = {
                    navController.popBackStack()
                }
            )
            if (state.isEmpty) {
                EmptyAppointmentScreen()
            } else {
                AppointmentList(
                    appointmentList = state.appointmentList,
                    onEditClick = {
                        navController.navigateRegisterAppointment(
                            isEdit = true,
                            chatAppointmentId = it
                        )
                    },
                    onDeleteClick = { appointmentViewModel.deleteAppointment(chatRoomId, it) },
                    onAddIconClick = {
                        navController.navigateRegisterAppointment(
                            isEdit = false,
                            chatAppointmentId = -1
                        )
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, bottom = 20.dp, top = 10.dp)
        ) {
            val buttonString = if (state.isEmpty) {
                stringResource(R.string.register_appointment_button)
            } else {
                stringResource(R.string.ready_vote)
            }
            LampButton(
                isGradient = true,
                buttonText = buttonString,
                onClick = {
                    navController.navigateRegisterAppointment(
                        isEdit = false,
                        chatAppointmentId = -1
                    )
                },
                enabled = true
            )
        }
    }
}

@Composable
fun EmptyAppointmentScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Text(
            text = stringResource(R.string.no_registered_appointment),
            color = Color.White,
            style = IncTypography.normal42
        )
        Text(
            text = stringResource(R.string.guide_make_appointment),
            color = Color.White,
            style = Typography.normal12,
            textAlign = TextAlign.Center
        )
    }
}
