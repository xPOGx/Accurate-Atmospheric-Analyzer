package mega.triple.aaa.presentation.core.ui.components.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import mega.triple.aaa.presentation.core.common.Constants
import mega.triple.aaa.presentation.core.ui.components.icon.CircleBgIcon
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun ParameterCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    descriptionTextStyle: TextStyle = typography.gs400size16,
    @DrawableRes iconRes: Int,
    extra: Pair<String, Boolean?> = "" to null
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.cardBG,
            contentColor = colors.cardContent,
        ),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    vertical = spaces.size8,
                    horizontal = spaces.size12,
                )
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircleBgIcon(iconRes = iconRes)
                Spacer(modifier = Modifier.width(spaces.size8))
                Column {
                    Text(
                        text = title,
                        style = typography.ps400size14,
                    )
                    Spacer(modifier = Modifier.height(spaces.size4))
                    Text(
                        text = description,
                        style = descriptionTextStyle,
                    )
                }
            }
            AAACardExtra(
                text = extra.first,
                isPositive = extra.second,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun AAACardExtra(
    modifier: Modifier = Modifier,
    text: String,
    isPositive: Boolean? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        isPositive?.let {
            val iconRotation: Float
            val iconTint: Color

            if (isPositive) {
                iconRotation = Constants.FLOAT_180
                iconTint = colors.changeGrowth
            } else {
                iconRotation = Constants.ZERO_FLOAT
                iconTint = colors.changeDecrease
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(spaces.size16)
                    .rotate(iconRotation),
            )
        }
        Text(
            text = text,
            style = typography.gs500size11,
        )
    }
}