package dot.adun.core.ui.theme.presets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import dot.adun.core.ui.components.loaders.LoaderAppearance
import dot.adun.core.ui.components.loaders.rememberCombinedLoaderColors
import dot.adun.core.ui.components.loaders.rememberSolidLoaderColors
import dot.adun.core.ui.components.loaders.rememberTwistedLoaderColors
import dot.adun.core.ui.theme.AdunColors

@Immutable
data class LoaderColorPresets(
    val solid: SolidSet,
    val combined: CombinedSet,
    val twisted: TwistedSet
) {
    sealed interface Set {
        val primary: LoaderAppearance
        val onPrimary: LoaderAppearance
        val surface: LoaderAppearance
        val background: LoaderAppearance
    }

    @Immutable
    data class SolidSet(
        override val primary: LoaderAppearance.Solid,
        override val onPrimary: LoaderAppearance.Solid,
        override val surface: LoaderAppearance.Solid,
        override val background: LoaderAppearance.Solid
    ) : Set

    @Immutable
    data class CombinedSet(
        override val primary: LoaderAppearance.Combined,
        override val onPrimary: LoaderAppearance.Combined,
        override val surface: LoaderAppearance.Combined,
        override val background: LoaderAppearance.Combined
    ) : Set

    @Immutable
    data class TwistedSet(
        override val primary: LoaderAppearance.Twisted,
        override val onPrimary: LoaderAppearance.Twisted,
        override val surface: LoaderAppearance.Twisted,
        override val background: LoaderAppearance.Twisted
    ) : Set

    companion object {
        @Composable
        fun create(colors: AdunColors): LoaderColorPresets {
            return LoaderColorPresets(
                solid = SolidSet(
                    primary = rememberSolidLoaderColors(colors.control.primary),
                    onPrimary = rememberSolidLoaderColors(colors.control.onPrimary),
                    surface = rememberSolidLoaderColors(colors.layer.background),
                    background = rememberSolidLoaderColors(colors.layer.surface)
                ),
                combined = CombinedSet(
                    primary = rememberCombinedLoaderColors(
                        layer1 = colors.control.primary,
                        layer2 = colors.control.secondary,
                        layer3 = colors.control.tertiary
                    ),
                    onPrimary = rememberCombinedLoaderColors(
                        layer1 = colors.control.onPrimary,
                        layer2 = colors.layer.primary,
                        layer3 = colors.layer.primaryDisabled
                    ),
                    surface = rememberCombinedLoaderColors(
                        layer1 = colors.layer.background,
                        layer2 = colors.layer.surface,
                        layer3 = colors.layer.onSurface
                    ),
                    background = rememberCombinedLoaderColors(
                        layer1 = colors.layer.onSurface,
                        layer2 = colors.layer.surface,
                        layer3 = colors.layer.background
                    )
                ),
                twisted = TwistedSet(
                    primary = rememberTwistedLoaderColors(
                        mainColor = colors.control.primary,
                        twinColor = colors.control.secondary,
                    ),
                    onPrimary = rememberTwistedLoaderColors(
                        mainColor = colors.control.onPrimary,
                        twinColor = colors.layer.primary,
                    ),
                    surface = rememberTwistedLoaderColors(
                        mainColor = colors.layer.background,
                        twinColor = colors.layer.onSurface,
                    ),
                    background = rememberTwistedLoaderColors(
                        mainColor = colors.layer.surface,
                        twinColor = colors.layer.onPrimary,
                    )
                )
            )
        }
    }
}
