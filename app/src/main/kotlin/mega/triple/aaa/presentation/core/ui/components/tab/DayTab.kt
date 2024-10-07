package mega.triple.aaa.presentation.core.ui.components.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.shapes
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun DayTab(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onSelect: ((Int) -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spaces.size16),
        modifier = modifier.fillMaxWidth(),
    ) {
        DayTabItem(
            title = "Today",
            onClick = { onSelect?.invoke(0) },
            selected = selectedIndex == 0,
            modifier = Modifier.weight(1f),
        )
        DayTabItem(
            title = "Tomorrow",
            onClick = { onSelect?.invoke(1) },
            selected = selectedIndex == 1,
            modifier = Modifier.weight(1f),
        )
        DayTabItem(
            title = "5 days",
            onClick = { onSelect?.invoke(2) },
            selected = selectedIndex == 2,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun DayTabItem(
    modifier: Modifier = Modifier,
    title: String = "Default Title",
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val indicationSource = remember { MutableInteractionSource() }

    val contentColor: Color = if (selected) colors.tabContent else colors.black

    val animatedContainerColor by animateColorAsState(
        targetValue = if (selected) colors.tabContainer else colors.white,
        label = "animatedContainerColor"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(animatedContainerColor, shapes.tabItemShape)
            .clickable(
                interactionSource = indicationSource,
                indication = null,
                onClick = { onClick?.invoke() },
                enabled = onClick != null,
            ),
    ) {
        Text(
            text = title,
            color = contentColor,
            style = typography.ps400size16,
            modifier = Modifier.padding(vertical = spaces.size8)
        )
    }
}
