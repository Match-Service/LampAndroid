package com.devndev.lamp.presentation.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

object DateFormatUtil {
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

    fun formatToDDay(input: String): String {
        val i = formatIsoToKoreanDate(input)
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH:mm", Locale.KOREA)
        val parsedDate = LocalDate.parse(i, formatter)
        val today = LocalDate.now()

        val daysDiff = ChronoUnit.DAYS.between(today, parsedDate)

        return when {
            daysDiff == 0L -> "D-DAY"
            daysDiff > 0L -> "D-$daysDiff"
            else -> "D+${-daysDiff}"
        }
    }
}
