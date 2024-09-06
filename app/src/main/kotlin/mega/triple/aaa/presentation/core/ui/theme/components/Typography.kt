package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mega.triple.aaa.R

data class Typography(
    val ps400size14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        fontFamily = productSansFamily,
    ),
    val ps400size16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        fontFamily = productSansFamily,
    ),
    val ps400size18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        fontFamily = productSansFamily,
    ),
    val ps400size22: TextStyle = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        fontFamily = productSansFamily,
    ),
    val ps700size18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W700,
        fontFamily = productSansFamily,
    ),
    val gs400size16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        fontFamily = googleSansFamily,
    ),
    val gs400size13: TextStyle = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.W400,
        fontFamily = googleSansFamily,
    ),
    val gs400size18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        fontFamily = googleSansFamily,
    ),
    val gs500size11: TextStyle = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        fontFamily = googleSansFamily,
    ),
    val gs500size14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        fontFamily = googleSansFamily,
    ),
) {
    companion object {
        private val productSansFamily: FontFamily = FontFamily(
            Font(R.font.product_sans_regular, FontWeight.W400),
            Font(R.font.product_sans_bold, FontWeight.W700),
        )
        private val googleSansFamily: FontFamily = FontFamily(
            Font(R.font.google_sans_regular, FontWeight.W400),
            Font(R.font.google_sans_medium, FontWeight.W500),
        )
    }
}

val LocalTypography = staticCompositionLocalOf { Typography() }
