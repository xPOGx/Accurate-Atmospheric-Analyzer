package mega.triple.aaa.ui.components.pulltorefresh

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import mega.triple.aaa.common.ext.safeLaunch
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.R.raw
import mega.triple.aaa.ui.components.ext.SpacerWidth
import mega.triple.aaa.ui.ext.formatLastUpdateTime
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.shapes
import mega.triple.aaa.ui.theme.AAATheme.spaces
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshWrapper(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    isRefreshing: Boolean,
    threshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    lastUpdateDate: Calendar? = null,
    enabled: Boolean = true,
    contentAlignment: Alignment = Alignment.TopStart,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val context = LocalContext.current
    val elevation = PullToRefreshDefaults.Elevation
    val containerColor = colors.cardBG
    val shape = shapes.roundedCustom(bottomStart = spaces.size32, bottomEnd = spaces.size32)
    val isNotReady = state.distanceFraction < 1
    val refreshTextRes =
        if (isNotReady) string.pull_to_refresh_pull else string.pull_to_refresh_release
    val refreshIconRotation by animateFloatAsState(targetValue = if (isNotReady) -90f else 90f)
    val maxSize = minOf(maxOf(0.35f, state.distanceFraction), 1f)
    val maxDistanceFraction = 2

    val compositionBackground by rememberLottieComposition(
        LottieCompositionSpec.RawRes(raw.lottie_animation_pull_to_refresh_background)
    )
    val backgroundProgress by animateLottieCompositionAsState(
        compositionBackground,
        iterations = LottieConstants.IterateForever,
    )
    val compositionLoading by rememberLottieComposition(
        LottieCompositionSpec.RawRes(raw.lottie_animation_pull_to_refresh_loading)
    )
    val loadingProgress by animateLottieCompositionAsState(
        compositionLoading,
        iterations = LottieConstants.IterateForever,
    )

    Box(
        modifier.pullToRefresh(
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            enabled = enabled,
        ),
        contentAlignment = contentAlignment,
    ) {
        content()
        Box(
            modifier = Modifier
                .graphicsLayer {
                    val showElevation = state.distanceFraction > 0f || isRefreshing
                    translationY = state.distanceFraction * threshold.roundToPx() - size.height
                    shadowElevation = if (showElevation) elevation.toPx() else 0f
                    this.shape = shape
                    clip = true
                }
                .background(color = containerColor, shape = shape)
                .align(Alignment.TopCenter)
                .fillMaxSize(maxSize),
            contentAlignment = Alignment.BottomCenter,
        ) {
            LottieAnimation(
                composition = compositionBackground,
                progress = { backgroundProgress },
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(threshold * state.distanceFraction * maxDistanceFraction),
            )
            AnimatedVisibility(
                visible = isRefreshing,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                LottieAnimation(
                    composition = compositionLoading,
                    progress = { loadingProgress },
                    modifier = Modifier.size(threshold),
                )
            }
            if (!isRefreshing) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = formatLastUpdateTime(context, lastUpdateDate),
                        color = colors.white,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Clip,
                        maxLines = 2,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(spaces.size4),
                    ) {
                        Text(
                            text = stringResource(refreshTextRes),
                            color = colors.white,
                        )
                        SpacerWidth(spaces.size12)
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = colors.white,
                            modifier = Modifier.rotate(refreshIconRotation)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PullToRefreshWrapperPreview() {
    val state = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    AAATheme {
        PullToRefreshWrapper(
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.safeLaunch {
                    isRefreshing = true
                    delay(2000)
                    isRefreshing = false
                }
            }
        ) {
            Text(
                "Hello world!",
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}
