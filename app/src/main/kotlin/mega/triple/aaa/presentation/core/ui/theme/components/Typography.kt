package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Typography(
    val h1Medium28: TextStyle =
        TextStyle(
            fontFamily = latoFamily,
            fontWeight = FontWeight.W500,
            fontSize = 28.sp,
            lineHeight = 36.sp,
        ),
) {
    companion object {
        private val latoFamily: FontFamily =
            FontFamily(
//                Font(R.font.lato_regular, FontWeight.W400),
            )
    }
}

val LocalTypography = staticCompositionLocalOf { Typography() }
