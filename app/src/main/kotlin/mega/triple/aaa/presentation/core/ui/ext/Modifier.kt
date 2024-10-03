package mega.triple.aaa.presentation.core.ui.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.noRippleClickable(
    onClick: (() -> Unit)? = null,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }

    this.then(
        Modifier
            .clickable(
                enabled = onClick != null,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick?.invoke() },
            ),
    )
}