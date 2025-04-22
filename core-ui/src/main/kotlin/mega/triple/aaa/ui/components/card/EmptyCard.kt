package mega.triple.aaa.ui.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import mega.triple.aaa.ui.R.raw
import mega.triple.aaa.ui.ext.noRippleClickable
import mega.triple.aaa.ui.theme.AAATheme

@Composable
fun EmptyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    val compositionBackground by rememberLottieComposition(
        LottieCompositionSpec.RawRes(raw.lottie_animation_empty_home_cards)
    )
    val backgroundProgress by animateLottieCompositionAsState(
        compositionBackground,
        iterations = LottieConstants.IterateForever,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        LottieAnimation(
            composition = compositionBackground,
            progress = { backgroundProgress },
            modifier = modifier
                .fillMaxWidth(0.5f)
                .noRippleClickable(onClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyCardPreview() {
    AAATheme {
        EmptyCard()
    }
}
