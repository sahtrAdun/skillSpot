package dot.adun.core.ui.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dot.adun.core.ui.modifiers.click.Clickable

@Composable
fun PrimaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.primary(state),
    content: @Composable () -> Unit
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier,
        content = content
    )
}

@Composable
fun PrimaryTextButton(
    text: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.primary(state),
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedContent(text) { animated ->
            Text(
                text = animated,
                style = config.textStyle()
            )
        }
    }
}

@Composable
fun SecondaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.secondary(state),
    content: @Composable () -> Unit
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier,
        content = content
    )
}

@Composable
fun SecondaryTextButton(
    text: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.secondary(state),
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedContent(text) { animated ->
            Text(
                text = animated,
                style = config.textStyle()
            )
        }
    }
}

@Composable
fun TertiaryButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.tertiary(state),
    content: @Composable () -> Unit
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier,
        content = content,
    )
}

@Composable
fun TertiaryTextButton(
    text: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.tertiary(state),
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedContent(text) { animated ->
            Text(
                text = animated,
                style = config.textStyle()
            )
        }
    }
}

@Composable
fun SurfaceButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.surface(state),
    content: @Composable () -> Unit
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier,
        content = content,
    )
}

@Composable
fun SurfaceTextButton(
    text: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.surface(state),
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedContent(text) { animated ->
            Text(
                text = animated,
                style = config.textStyle()
            )
        }
    }
}

@Composable
fun BackgroundButton(
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.background(state),
    content: @Composable () -> Unit
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier,
        content = content,
    )
}

@Composable
fun BackgroundTextButton(
    text: String,
    clickable: Clickable,
    modifier: Modifier = Modifier,
    state: ButtonState = rememberButtonState(),
    config: ButtonConfig = ButtonConfig.background(state),
) {
    BaseButton(
        clickable = clickable,
        config = config,
        modifier = modifier.animateContentSize()
    ) {
        AnimatedContent(text) { animated ->
            Text(
                text = animated,
                style = config.textStyle()
            )
        }
    }
}
