package mega.triple.aaa.ui.components.toolbar

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import mega.triple.aaa.common.ext.Constants.TOOLBAR_HEIGHT_MAX
import mega.triple.aaa.common.ext.Constants.TOOLBAR_HEIGHT_MIN
import mega.triple.aaa.strings.R
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.ext.SpacerHeight
import mega.triple.aaa.ui.components.tab.DayTab
import mega.triple.aaa.ui.ext.formatFeelTemperature
import mega.triple.aaa.ui.ext.formatPartTemperature
import mega.triple.aaa.ui.ext.formatTemperature
import mega.triple.aaa.ui.ext.formatTime
import mega.triple.aaa.ui.ext.getAccuWeatherIconRes
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.shapes
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    compact: Boolean,
    selectedIndex: Int,
    locationName: String? = null,
    data: DailyForecastUiModel? = null,
    onSelect: ((Int) -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
) {
    val density = LocalDensity.current
    val context = LocalContext.current
    val statusBars = WindowInsets.statusBars.getTop(density) / density.density
    var min by remember { mutableStateOf((TOOLBAR_HEIGHT_MIN + statusBars).dp) }
    var max by remember { mutableStateOf((TOOLBAR_HEIGHT_MAX + statusBars).dp) }

    LaunchedEffect(statusBars) {
        val newMin = (TOOLBAR_HEIGHT_MIN + statusBars).dp
        if (newMin != min) {
            min = newMin
            max = (TOOLBAR_HEIGHT_MAX + statusBars).dp
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
        targetValue = if (compact) 60.dp else 75.dp,
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

    val temperatureUnit = data?.temperature?.maximum?.unit
    val feelLikeTemp = data?.realFeelTemperature?.mathAverage
    val feelLikeShadowTemp = data?.realFeelTemperatureShade?.mathAverage
    val icon = getAccuWeatherIconRes(data?.day?.icon)
    val iconPhrase = data?.day?.iconPhrase ?: STUB_VALUE

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
                    painter = painterResource(drawable.img_bg_toolbar),
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
                    .statusBarsPadding(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = spaces.size24),
                ) {
                    Text(
                        text = locationName ?: stringResource(R.string.toolbar_unknown_place),
                        color = mainColor,
                        style = typography.ps400size22,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        IconButton(onClick = { onSearch?.invoke() }) {
                            Icon(
                                painter = painterResource(drawable.ic_search),
                                contentDescription = null,
                                tint = mainColor,
                            )
                        }
                        IconButton(onClick = { onSettings?.invoke() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = mainColor,
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = if (compact) Alignment.CenterVertically else Alignment.Bottom,
                    modifier = Modifier.padding(horizontal = spaces.size24)
                ) {
                    Text(
                        text = formatTemperature(
                            data?.day?.wetBulbTemperature?.average?.value
                                ?: data?.day?.wetBulbTemperature?.mathAverage,
                            data?.day?.wetBulbTemperature?.maximum?.unit,
                        ),
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
                            text = formatFeelTemperature(context, feelLikeTemp, temperatureUnit),
                            style = typography.ps400size18.copy(fontSize = animateFeelSize.sp),
                            color = mainColor,
                        )
                        androidx.compose.animation.AnimatedVisibility(!compact) {
                            Text(
                                text = formatFeelTemperature(
                                    context,
                                    feelLikeShadowTemp,
                                    temperatureUnit,
                                    inShadow = true
                                ),
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
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(animateImageSize)
                        )
                        AnimatedVisibility(
                            visible = !compact,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            SpacerHeight(spaces.size16)
                            Text(
                                text = iconPhrase,
                                style = typography.ps400size22,
                                color = fullColor,
                                textAlign = TextAlign.End,
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
                                text = formatTime(data?.date),
                                style = typography.ps400size18,
                                color = fullColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = formatPartTemperature(
                                        context,
                                        data?.temperature?.maximum?.value,
                                        true,
                                        temperatureUnit
                                    ),
                                    style = typography.ps700size18,
                                    color = fullColor
                                )
                                Text(
                                    text = formatPartTemperature(
                                        context,
                                        data?.temperature?.minimum?.value,
                                        false,
                                        temperatureUnit
                                    ),
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
