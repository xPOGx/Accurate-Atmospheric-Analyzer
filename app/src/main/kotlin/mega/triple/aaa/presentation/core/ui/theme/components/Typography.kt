package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mega.triple.aaa.R

data class Typography(
    val ps400size14: TextStyle =
        TextStyle(
            fontFamily = productSansFamily,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
        ),
) {
    companion object {
        private val productSansFamily: FontFamily =
            FontFamily(
                Font(R.font.product_sans_regular, FontWeight.W400),
                Font(R.font.product_sans_bold, FontWeight.W700),
            )
        private val googleSansFamily: FontFamily =
            FontFamily(
                Font(R.font.google_sans_regular, FontWeight.W400),
                Font(R.font.google_sans_medium, FontWeight.W500),
            )
    }
}

val LocalTypography = staticCompositionLocalOf { Typography() }
