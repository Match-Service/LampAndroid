package com.devndev.lamp.presentation.ui.appointment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.domain.model.chat.AppointmentItem
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AppointmentList(appointmentList: List<AppointmentItem>) {
    // todo 내가 준비 했을 경우 string 변경
//    val voteString = if (appointment.voteReadyUserCount == 0) {
//        stringResource(R.string.register_appointment_ready_vote)
//    } else {
//        stringResource(R.string.vote_ready_count_message, appointment.voteReadyUserCount)
//    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.message_icon),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = stringResource(R.string.register_appointment_ready_vote),
                style = Typography.normal14,
                color = Color.White
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(appointmentList) { appointment ->
                Appointment(appointment)
            }
        }
    }
}

@Composable
fun Appointment(appointment: AppointmentItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val nameColor = if (appointment.gender == "MALE") {
                ManColor
            } else {
                WomanColor
            }

            Text(
                text = stringResource(R.string.appointment_suggestion, appointment.userName),
                style = Typography.medium18,
                color = nameColor
            )

            if (appointment.isMine) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_icon),
                        contentDescription = "Edit Appointment",
                        tint = LightGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "Delete Appointment",
                        tint = LightGray,
                        modifier = Modifier.height(18.dp).width(14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.clock_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = formatIsoToKoreanDate(appointment.appointment.meetingTime),
                color = Color.White,
                style = Typography.medium15
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.region_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = appointment.appointment.location,
                color = Color.White,
                style = Typography.medium15
            )
        }
    }
}

fun formatIsoToKoreanDate(isoString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy년 M월 d일 HH:mm", Locale.getDefault())

        val date = inputFormat.parse(isoString)
        if (date != null) {
            val calendar = Calendar.getInstance().apply {
                time = date
                add(Calendar.HOUR_OF_DAY, -9) // 9시간 빼기
            }
            outputFormat.format(calendar.time)
        } else {
            ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
