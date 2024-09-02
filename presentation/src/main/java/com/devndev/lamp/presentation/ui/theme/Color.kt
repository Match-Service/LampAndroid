package com.devndev.lamp.presentation.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BackGroundColor = Color.Black

val ManColor = Color(0xFF16B9F7)
val WomanColor = Color(0xFFE4313C)

// 남녀 색 구분 필요
var MainColor = Color(0xFF16B9F7)

fun getMainColor(sex: String) {
    MainColor = if (sex == "man") {
        ManColor
    } else {
        WomanColor
    }
}

val Gray = Color(0xFF373737)
