package com.astralai.pokemoncards.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = PokemonCardsColors(
    /*primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,*/
    uiFloated = FunctionalGrey,
    uiBackground = Neutral0,
    isDark = false

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val DarkColorPalette = PokemonCardsColors(
    /*primary = Color.Yellow,
    primaryVariant = Purple700,
    secondary = Teal200,*/
    uiFloated = FunctionalDarkGrey,
    uiBackground = Neutral8,
    isDark = true
)

@Composable
fun PokemonCardsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvidePokemonCardsColors(colors) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object PokemonCardsTheme {
    val colors: PokemonCardsColors
        @Composable
        get() = LocalPokemonCardsColors.current
}

private val LocalPokemonCardsColors = staticCompositionLocalOf<PokemonCardsColors> {
    error("No PokemonCardsColors provided")
}

/**
 * PokemonCards custom Color Palette
 */
@Stable
class PokemonCardsColors(
    uiBackground: Color,
    uiFloated: Color,
    isDark: Boolean
) {
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiFloated by mutableStateOf(uiFloated)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: PokemonCardsColors) {
        uiBackground = other.uiBackground
        uiFloated = other.uiFloated
        isDark = other.isDark
    }

    fun copy(): PokemonCardsColors = PokemonCardsColors(
        uiBackground = uiBackground,
        uiFloated = uiFloated,
        isDark = isDark
    )
}

@Composable
fun ProvidePokemonCardsColors(
    colors: PokemonCardsColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalPokemonCardsColors provides colorPalette, content = content)
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colors] in preference to [PokemonCardsTheme.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)