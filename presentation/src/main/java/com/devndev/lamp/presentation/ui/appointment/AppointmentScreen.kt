package com.devndev.lamp.presentation.ui.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.devndev.lamp.presentation.ui.common.AppointmentStatus
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup

@Composable
fun AppointmentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    chatRoomId: Int,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val state by appointmentViewModel.uiState.collectAsStateWithLifecycle()
    var isAppointmentClickable by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf(state.getVotedAppointment()) }

    var isVoteConfirmPopupShow by remember { mutableStateOf(false) }
    var isReadyConfirmPopupShow by remember { mutableStateOf(false) }
    var isDeletePopupShow by remember { mutableStateOf(false) }
    var deleteAppointmentId by remember { mutableStateOf(0) }

    if (isVoteConfirmPopupShow) {
        TwoButtonPopup(
            mainText = stringResource(R.string.vote_popup_title),
            hintText = stringResource(R.string.vote_popup_message),
            startButtonText = stringResource(R.string.cancel),
            endButtonText = stringResource(R.string.confirm),
            onStartButtonClick = { isVoteConfirmPopupShow = false },
            onEndButtonClick = {
                isVoteConfirmPopupShow = false
                appointmentViewModel.voteAppointment(
                    chatAppointmentId = selectedAppointment?.appointment?.chatAppointmentId
                        ?: -1,
                    onSuccess = { navController.popBackStack() }
                )
            }
        )
    }

    if (isReadyConfirmPopupShow) {
        TwoButtonPopup(
            mainText = stringResource(R.string.ready_popup_title),
            hintText = stringResource(R.string.ready_popup_message),
            startButtonText = stringResource(R.string.cancel),
            endButtonText = stringResource(R.string.confirm),
            onStartButtonClick = { isReadyConfirmPopupShow = false },
            onEndButtonClick = {
                appointmentViewModel.readyVote(chatRoomId)
                isReadyConfirmPopupShow = false
            }
        )
    }

    if (isDeletePopupShow) {
        TwoButtonPopup(
            mainText = stringResource(R.string.delete_popup_title),
            hintText = stringResource(R.string.delete_popup_message),
            startButtonText = stringResource(R.string.cancel),
            endButtonText = stringResource(R.string.confirm),
            onStartButtonClick = { isDeletePopupShow = false },
            onEndButtonClick = {
                appointmentViewModel.deleteAppointment(chatRoomId, deleteAppointmentId)
                isDeletePopupShow = false
                deleteAppointmentId = 0
            }
        )
    }

    val topSectionText = when (state.getAppointmentStatus()) {
        AppointmentStatus.BEFORE_READY -> stringResource(R.string.register_appointment_ready_vote)
        AppointmentStatus.WAITING_READY -> stringResource(
            R.string.vote_ready_count_message,
            state.appointment?.voteReadyUserCount ?: ""
        )

        AppointmentStatus.BEFORE_VOTE -> stringResource(R.string.do_vote)
        AppointmentStatus.WAITING_VOTE -> stringResource(
            R.string.vote_count_message,
            state.getVoteCount()
        )

        else -> ""
    }

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
                    topSectionText = topSectionText,
                    selectedAppointment = selectedAppointment,
                    onEditClick = {
                        navController.navigateRegisterAppointment(
                            isEdit = true,
                            chatAppointmentId = it
                        )
                    },
                    onDeleteClick = {
                        isDeletePopupShow = true
                        deleteAppointmentId = it
                    },
                    onAddIconClick = {
                        navController.navigateRegisterAppointment(
                            isEdit = false,
                            chatAppointmentId = -1
                        )
                    },
                    onAppointmentSelected = {
                        selectedAppointment = it
                    },
                    isAppointmentClickable = isAppointmentClickable,
                    appointmentStatus = state.getAppointmentStatus()
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, bottom = 20.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var buttonString = ""
            var buttonEnable = false
            when (state.getAppointmentStatus()) {
                AppointmentStatus.EMPTY_APPOINTMENT -> {
                    buttonString = stringResource(R.string.register_appointment_button)
                    buttonEnable = true
                    isAppointmentClickable = false
                }

                AppointmentStatus.BEFORE_READY -> {
                    buttonString = stringResource(R.string.ready_vote)
                    buttonEnable = true
                    isAppointmentClickable = false
                }

                AppointmentStatus.WAITING_READY -> {
                    buttonString = stringResource(R.string.waiting_ready_vote)
                    buttonEnable = false
                    isAppointmentClickable = false
                }

                AppointmentStatus.BEFORE_VOTE -> {
                    buttonString = stringResource(R.string.vote)
                    buttonEnable = selectedAppointment != null
                    isAppointmentClickable = true
                }

                AppointmentStatus.WAITING_VOTE -> {
                    buttonString = stringResource(R.string.waiting_vote)
                    buttonEnable = false
                    isAppointmentClickable = false
                }

//                AppointmentStatus.CONFIRM_APPOINTMENT -> {
//                    buttonString = ""
//                    buttonEnable = false
//                    isAppointmentClickable = false
//                }

                else -> {}
            }
            if (!state.isEmpty && state.appointment?.isReady != true) {
                Text(
                    text = stringResource(
                        R.string.guide_ready_vote,
                        state.appointment?.allUserCount ?: ""
                    ),
                    color = Color.White,
                    style = Typography.normal12
                )
            }
            LampButton(
                isGradient = true,
                buttonText = buttonString,
                onClick = {
                    when (state.getAppointmentStatus()) {
                        AppointmentStatus.EMPTY_APPOINTMENT -> {
                            navController.navigateRegisterAppointment(
                                isEdit = false,
                                chatAppointmentId = -1
                            )
                        }

                        AppointmentStatus.BEFORE_READY -> {
                            isReadyConfirmPopupShow = true
                        }

                        AppointmentStatus.BEFORE_VOTE -> {
                            isVoteConfirmPopupShow = true
                        }
                    }
                },
                enabled = buttonEnable
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
