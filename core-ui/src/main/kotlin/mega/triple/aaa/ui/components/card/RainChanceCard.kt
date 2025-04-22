package mega.triple.aaa.ui.components.card

import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.components.icon.CircleBgIcon
import mega.triple.aaa.ui.ext.formatProbability
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography
import mega.triple.aaa.ui.R.drawable as drawableRes

@Composable
fun RainChanceCard(
    modifier: Modifier = Modifier,
    @IntRange(0, 100) dayChance: Int = 0,
    @IntRange(0, 100) nightChance: Int = 0,
) {
    Surface(
        color = colors.cardBG,
        contentColor = colors.cardContent,
        modifier = modifier
            .clip(AAATheme.shapes.cardShape),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spaces.size12),
            modifier = Modifier.padding(bottom = spaces.size22),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spaces.size8),
                modifier = Modifier
                    .padding(spaces.size12)
                    .fillMaxWidth()
            ) {
                CircleBgIcon(iconRes = drawableRes.ic_rainy)
                Text(
                    text = stringResource(string.home_rain_forecast),
                    style = typography.ps400size14,
                )
            }
            RainChance(
                title = stringResource(string.common_day),
                chance = dayChance,
            )
            RainChance(
                title = stringResource(string.common_night),
                chance = nightChance,
            )
        }
    }
}

@Composable
private fun RainChance(
    modifier: Modifier = Modifier,
    @IntRange(0, 100) chance: Int = 0,
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = spaces.size16),
    ) {
        Text(
            text = title,
            style = typography.gs400size16,
            color = colors.cardContent,
            modifier = Modifier.weight(0.2f),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(spaces.size24)
                .background(color = colors.rainChance, shape = CircleShape)
                .clip(CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(chance / 100f)
                    .height(spaces.size24)
                    .background(color = colors.changeGrowth, shape = CircleShape),
            )
        }
        Text(
            text = formatProbability(chance),
            style = typography.ps400size16,
            color = colors.cardContent,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.15f),
        )
    }
}

@Preview
@Composable
private fun RainChancePreview() = AAATheme {
    RainChance(
        chance = 50,
        title = "Rain",
    )
}

@Preview
@Composable
private fun RainChanceCardPreview() = AAATheme {
    RainChanceCard(
        dayChance = 25,
        nightChance = 75,
    )
}

