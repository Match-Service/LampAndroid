package com.devndev.lamp.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.R

internal val incheon = FontFamily(
    Font(R.font.incheon_font, FontWeight.Normal)
)

internal val pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_thin, FontWeight.Thin),
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium)
)

@Immutable
data class IncheonTypography(
    val normal42: TextStyle
)

@Immutable
data class PretendardTypography(
    val semiBold32: TextStyle,
    val semiBold25: TextStyle,
    val semiBold20: TextStyle,
    val normal12: TextStyle,
    val normal14: TextStyle,
    val normal15: TextStyle,
    val normal9: TextStyle,
    val medium18: TextStyle,
    val medium15: TextStyle,
    val medium12: TextStyle
)

val IncTypography = IncheonTypography(
    normal42 = incheonTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 42
    )
)

// Set of Material typography styles to start with
val Typography = PretendardTypography(
    semiBold32 = pretendardTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32
    ),
    semiBold25 = pretendardTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 25
    ),
    semiBold20 = pretendardTextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20
    ),
    normal12 = pretendardTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12
    ),
    normal14 = pretendardTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14
    ),
    normal15 = pretendardTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15
    ),
    normal9 = pretendardTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 9
    ),
    medium18 = pretendardTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18
    ),
    medium15 = pretendardTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15
    ),
    medium12 = pretendardTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12
    )
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
)

private fun pretendardTextStyle(
    fontSize: Int,
    fontWeight: FontWeight
) = TextStyle(
    fontFamily = pretendard,
    fontWeight = fontWeight,
    fontSize = fontSize.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

private fun incheonTextStyle(
    fontSize: Int,
    fontWeight: FontWeight
) = TextStyle(
    fontFamily = incheon,
    fontWeight = fontWeight,
    fontSize = fontSize.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)
