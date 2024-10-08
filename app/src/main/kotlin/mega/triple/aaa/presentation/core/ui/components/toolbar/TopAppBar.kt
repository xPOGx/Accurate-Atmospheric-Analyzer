package mega.triple.aaa.presentation.core.ui.components.toolbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.common.Constants.TOOLBAR_HEIGHT_MAX
import mega.triple.aaa.presentation.core.common.Constants.TOOLBAR_HEIGHT_MIN
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerHeight
import mega.triple.aaa.presentation.core.ui.components.tab.DayTab
import mega.triple.aaa.presentation.core.ui.ext.formatFeelTemperature
import mega.triple.aaa.presentation.core.ui.ext.formatPartTemperature
import mega.triple.aaa.presentation.core.ui.ext.formatTemperature
import mega.triple.aaa.presentation.core.ui.ext.formatTime
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.shapes
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import java.util.Date

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    compact: Boolean,
    selectedIndex: Int,
    locationName: String? = null,
    onSelect: ((Int) -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
) {
    val density = LocalDensity.current
    val cutout = WindowInsets.displayCutout.getTop(density) / density.density
    var min by remember { mutableStateOf((TOOLBAR_HEIGHT_MIN + cutout).dp) }
    var max by remember { mutableStateOf((TOOLBAR_HEIGHT_MAX + cutout).dp) }

    LaunchedEffect(cutout) {
        val newMin = (TOOLBAR_HEIGHT_MIN + cutout).dp
        if (newMin != min) {
            min = newMin
            max = (TOOLBAR_HEIGHT_MAX + cutout).dp
        }
    }

    val fullColor = colors.white
    val compactColor = colors.black
    val mainColor = if (compact) compactColor else fullColor

    val animateHeight by animateDpAsState(
        targetValue = if (compact) min else max,
        label = "animateHeight",
    )
    val animateTempSize by animateIntAsState(
        targetValue = if (compact) 57 else 122,
        label = "animateTempSize",
    )
    val animateFeelSize by animateFloatAsState(
        targetValue = if (compact) 16f else 18f,
        label = "animateFeelSize",
    )
    val animateFeelOffset by animateOffsetAsState(
        targetValue = if (compact) {
            Offset(0f, -(10 * density.density))
        } else {
            Offset(-(20 * density.density), -(30 * density.density))
        },
        label = "animateFeelOffset",
    )
    val animateImageSize by animateDpAsState(
        targetValue = if (compact) 60.dp else 108.dp,
        label = "animateImageSize",
    )
    val animateShapeSize by animateDpAsState(
        targetValue = if (compact) Dp.Hairline else 32.dp,
        label = "animateShape",
    )
    val mainShape = shapes.roundedCustom(
        bottomStart = animateShapeSize,
        bottomEnd = animateShapeSize
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height = animateHeight)
            .background(
                color = colors.toolbarBG,
                shape = mainShape,
            )
            .clip(mainShape)
    ) {
        Box {
            this@Column.AnimatedVisibility(
                visible = !compact,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Image(
                    painter = painterResource(R.drawable.img_bg_toolbar),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeightIn(max, max),
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .displayCutoutPadding(),
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = spaces.size24),
                ) {
                    Text(
                        text = locationName ?: "Unknown place",
                        color = mainColor,
                        style = typography.ps400size22,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { onSearch?.invoke() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            tint = mainColor,
                        )
                    }
                }
                Row(
                    verticalAlignment = if (compact) Alignment.CenterVertically else Alignment.Bottom,
                    modifier = Modifier.padding(horizontal = spaces.size24),
                ) {
                    Text(
                        text = formatTemperature(3),
                        style = typography.ps400size14.copy(fontSize = animateTempSize.sp),
                        color = mainColor,
                    )
                    Column(
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = animateFeelOffset.x
                                translationY = animateFeelOffset.y
                            }
                            .align(Alignment.Bottom)
                    ) {
                        Text(
                            text = formatFeelTemperature(-2),
                            style = typography.ps400size18.copy(fontSize = animateFeelSize.sp),
                            color = mainColor,
                        )
                        androidx.compose.animation.AnimatedVisibility(!compact) {
                            Text(
                                text = formatFeelTemperature(-3, inShadow = true),
                                style = typography.ps400size18.copy(fontSize = animateFeelSize.sp),
                                color = mainColor,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_weather_temp),
                            contentDescription = null,
                            modifier = Modifier.size(animateImageSize)
                        )
                        SpacerHeight(spaces.size16)
                        AnimatedVisibility(
                            visible = !compact,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "Cloudy",
                                style = typography.ps400size22,
                                color = fullColor,
                            )
                        }
                    }
                }
                AnimatedContent(
                    targetState = compact,
                    label = "ToolbarFooter",
                    transitionSpec = {
                        if (compact) {
                            // compact -> full
                            (slideInVertically { it }).togetherWith(slideOutVertically { -2 * it })
                        } else {
                            // full -> compact
                            (slideInVertically { -it }).togetherWith(slideOutVertically { 2 * it })
                        }
                    },
                ) { small ->
                    if (small) {
                        DayTab(
                            selectedIndex = selectedIndex,
                            onSelect = onSelect,
                            modifier = Modifier
                                .padding(horizontal = spaces.size16)
                                .padding(bottom = spaces.size12)
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .padding(horizontal = spaces.size24)
                                .padding(bottom = spaces.size16),
                        ) {
                            Text(
                                text = formatTime(Date()),
                                style = typography.ps400size18,
                                color = fullColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = formatPartTemperature(5, true),
                                    style = typography.ps700size18,
                                    color = fullColor
                                )
                                Text(
                                    text = formatPartTemperature(-6, false),
                                    style = typography.ps700size18,
                                    color = fullColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    AAATheme {
        TopAppBar(
            compact = false,
            selectedIndex = 0,
        )
    }
}

@Preview
@Composable
private fun TopAppBarPreviewCompact() {
    AAATheme {
        TopAppBar(
            compact = true,
            selectedIndex = 0,
        )
    }
}
